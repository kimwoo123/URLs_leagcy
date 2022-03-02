from fastapi import Depends, APIRouter, HTTPException, status, Response
from models.folder import User, UserIn, FolderOut
from config.db import db
from serializers.common import serializeDict
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pymongo import ReturnDocument
from .token import get_current_user

folder_user = APIRouter()

# 내 권한 확인
async def permission_check_me(folder_id, current_user_email):
    me = db.folder.find_one({"_id": ObjectId(folder_id), "users.email": current_user_email}, {"users.$":1})
    if me is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user me not found")
    elif me["users"][0]["permission"] == 0:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=f"permission 0 is read only")


# 상대 권한 확인
async def permission_check_user_in(folder_id, user_in_email):
    target_user = db.folder.find_one({"_id": ObjectId(folder_id), "users.email": user_in_email}, {"users.$":1})
    if target_user is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {user_in_email} not found")
    elif target_user["users"][0]["permission"] == 2:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=f"permission 2 is owner")


@folder_user.post('/folder/{folder_id}/user', summary="폴더 유저 생성", response_model=FolderOut)
async def create_folder_user(folder_id, user_in: UserIn, current_user: User = Depends(get_current_user)):
    await permission_check_me(folder_id, current_user["email"])

    # 유저 찾기
    target_user = db.user.find_one({"email": user_in.email})
    if target_user is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {user_in.email} not found")

    is_user_in_folder = db.folder.find_one({"_id": ObjectId(folder_id), "users.email": user_in.email})
    if is_user_in_folder:
        raise HTTPException(status_code=status.HTTP_409_CONFLICT)

    if user_in.permission > 1:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=f"permission 2 is owner")

    user = User(**target_user, permission=user_in.permission)
    # 폴더에 유저 push
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)}, {"$push": {"users": dict(user)}, "$set": {"shared": True}},
        return_document=ReturnDocument.AFTER
    )
    for user in folder["users"]:
        db.user.find_one_and_update(
            {"email": user["email"], "folders.folder_id": ObjectId(folder_id)}, 
            {"$set": {"folders.$.shared": True}}
        )
    # 유저의 폴더에 폴더 push
    taget_user_folder = {
      "folder_id": folder["_id"],
      "folder_name": folder["folder_name"],
      "shared": folder["shared"]
    }
    db.user.find_one_and_update({"_id": ObjectId(target_user["_id"])}, {"$push": {"folders": taget_user_folder}})

    if folder is not None:
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_user.put('/folder/{folder_id}/user', summary="폴더 유저 권한 변경", response_model=FolderOut)
async def update_folder_user(folder_id, user_in: UserIn, current_user: User = Depends(get_current_user)):
    await permission_check_me(folder_id, current_user["email"])
    await permission_check_user_in(folder_id, user_in.email)

    if user_in.permission > 1:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=f"permission 2 is owner")

    # 폴더에 유저 권한 변경
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id), "users.email": user_in.email},
        {"$set": {"users.$.permission": user_in.permission}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} or useremail not found")


@folder_user.delete('/folder/{folder_id}/user', summary="폴더 유저 삭제", response_model=FolderOut)
async def delete_folder_user(folder_id, email, current_user: User = Depends(get_current_user)):
    await permission_check_me(folder_id, current_user["email"])
    await permission_check_user_in(folder_id, email)

    # 폴더에서 유저 삭제
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)},
        {"$pull": {"users": {"email": email}}},
        return_document=ReturnDocument.AFTER
    )
    # 유저의 폴더에서 폴더 삭제
    db.user.find_one_and_update(
        {"email": email},
        {"$pull": {"folders": {"folder_id": ObjectId(folder_id)}}},
        return_document=ReturnDocument.AFTER
    )
    if folder is not None:
        if len(folder["users"]) == 1:
            db.folder.find_one_and_update(
                {"_id": ObjectId(folder_id)},
                {"$set": {"shared": False}},
            )
            db.user.find_one_and_update(
                {"_id": current_user["_id"], "folders.folder_id": ObjectId(folder_id)}, 
                {"$set": {"folders.$.shared": False}},
            )
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_user.delete('/folder/{folder_id}/me', summary="폴더에서 자신 삭제 (폴더 탈퇴)", status_code=status.HTTP_204_NO_CONTENT)
async def delete_folder_user(folder_id, current_user: User = Depends(get_current_user)):

    # 폴더에서 유저 삭제
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)},
        {"$pull": {"users": {"email": current_user["email"]}}},
        return_document=ReturnDocument.AFTER
    )
    # 유저의 폴더에서 폴더 삭제
    db.user.find_one_and_update(
        {"_id": current_user["_id"]},
        {"$pull": {"folders": {"folder_id": ObjectId(folder_id)}}}
    )
    if folder is not None:
        if len(folder["users"]) == 1:
            db.folder.find_one_and_update(
                {"_id": ObjectId(folder_id)},
                {"$set": {"shared": False}},
            )
            db.user.find_one_and_update(
                {"email": folder["users"][0]["email"], "folders.folder_id": ObjectId(folder_id)}, 
                {"$set": {"folders.$.shared": False}},
            )
        return Response(status_code=status.HTTP_204_NO_CONTENT)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")
