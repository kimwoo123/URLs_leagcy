from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User, FolderIn, FolderInDB, FolderOut
from config.db import db
from serializers.common import serializeDict, serializeList
from serializers.common import serializeList_folder
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pymongo import ReturnDocument, DESCENDING, ASCENDING
from .token import get_current_user
from .folder_url import tag_count_decrease


folder = APIRouter()


@folder.get('/folder/me', summary="내 모든 폴더 조회")
async def find_all_folder(current_user: User = Depends(get_current_user)):
    me = db.user.find_one({"_id": ObjectId(current_user["_id"])})
    if me is not None:
        return serializeList_folder(me["folders"])
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND)


@folder.get('/folder/{id}', summary="폴더 상세 조회")
async def find_one_folder(id):
    folder = db.folder.find_one({"_id": ObjectId(id)})
    
    if folder is not None:
        for idx, url in enumerate(folder["urls"]):
            memo = db.memo.find_one({"_id": ObjectId(folder["urls"][idx]["memos_id"])})
            folder["urls"][idx]["memos_count"] = len(memo["memos"])
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
        
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.post('/folder', summary="단일 폴더 생성", response_model=FolderOut)
async def create_folder(folder_in: FolderIn, current_user: User = Depends(get_current_user)):
    if db.user.find_one({"_id": ObjectId(current_user["_id"]), "folders.folder_name": folder_in.folder_name}):
        raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail=f"folder {folder_in.folder_name} is already exists")

    result = db.folder.insert_one(jsonable_encoder(FolderInDB(**folder_in.dict(), users=[User(**current_user, permission=2)])))

    user_folder = {
      "folder_id": result.inserted_id,
      "folder_name": folder_in.folder_name,
      "shared": False
    }
    db.user.find_one_and_update({"_id": ObjectId(current_user["_id"])}, {"$push": {"folders": user_folder}})
    
    new_folder = db.folder.find_one({"_id": ObjectId(result.inserted_id)})
    return serializeDict(new_folder)


@folder.put('/folder/{id}', summary="폴더명 변경", response_model=FolderOut)
async def update_folder(id, folder_in: FolderIn, current_user: User = Depends(get_current_user)):
    if db.user.find_one({"_id": ObjectId(current_user["_id"]), "folders.folder_id": ObjectId(id)}):
        
        if db.user.find_one({"_id": ObjectId(current_user["_id"]), "folders.folder_name": folder_in.folder_name}):
            raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail=f"folder {folder_in.folder_name} is already exists")

        folder = db.folder.find_one_and_update(
            {"_id": ObjectId(id)}, {"$set": {"folder_name": folder_in.folder_name}}, 
            return_document=ReturnDocument.AFTER
        )
        if folder is not None:
            for user in folder["users"]:
                db.user.find_one_and_update(
                    {"email": user["email"], "folders.folder_id": ObjectId(id)},
                    {"$set": {"folders.$.folder_name": folder_in.folder_name}}
                )
            folder["urls"] = folder["urls"][::-1]
            return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")


@folder.delete('/folder/{id}', summary="폴더 삭제", response_model=FolderOut)
async def delete_folder(id, current_user: User = Depends(get_current_user)):
    if db.user.find_one({"_id": ObjectId(current_user["_id"]), "folders.folder_id": ObjectId(id)}):

        user = db.folder.find_one({"_id": ObjectId(id), "users.email": current_user["email"]}, {"users.$": 1})
        if user["users"][0]["permission"] == 0:
            raise HTTPException(status_code=status.HTTP_403_FORBIDDEN)
            
        folder = db.folder.find_one_and_delete({"_id": ObjectId(id)})

        if folder is not None:
            urls = folder["urls"]
            for url in urls:
                await tag_count_decrease(url["tags"], user_email=url["added_by"]["email"])
                db.memo.delete_one({"_id": ObjectId(url["memos_id"])})

            users = folder["users"]
            for user in users:
                db.user.find_one_and_update(
                    {"email": user["email"]},
                    {"$pull": {"folders": {"folder_id": ObjectId(id)}}}
                )
            folder["urls"] = folder["urls"][::-1]
            return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {id} not found")