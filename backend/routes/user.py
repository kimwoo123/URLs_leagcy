from typing import List

from bson import ObjectId
from fastapi import Depends, APIRouter, HTTPException, status, Response
from fastapi.encoders import jsonable_encoder
from pymongo import ReturnDocument

from config.db import db
from models.recommend import UrlIn
from models.user import UserIn, UserInDB, UserOut
from serializers.common import serializeDict, serializeList
from .token import get_current_user

user = APIRouter()


@user.get('/user', summary="모든 유저 조회", response_model=List[UserOut])
async def find_all_users():
    return serializeList(db.user.find())


@user.get('/user/{id}', summary="단일 유저 조회", response_model=UserOut)
async def find_one_user(id):
    user = db.user.find_one({"_id": ObjectId(id)})
    if user is not None:
        return serializeDict(user)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {id} not found")


# 토큰 추가 예정
@user.post('/user', summary="유저 정보 업데이트 혹은 회원가입", response_model=UserOut, status_code=status.HTTP_201_CREATED)
async def create_update_user(user_in: UserIn):
    # 유저 있을 때 정보 업데이트
    update_user = db.user.find_one_and_update(
        {"email": user_in.email}, {"$set": {"nickname": user_in.nickname, "avatar": user_in.avatar}},
        return_document=ReturnDocument.AFTER
    )
    # 유저 없으면 생성
    if update_user is None:
        user = UserInDB(**dict(user_in))
        tmp = db.user.insert_one(jsonable_encoder(user))
        create_user = db.user.find_one({"_id": ObjectId(tmp.inserted_id)})
        if create_user is not None:
            return serializeDict(create_user)
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {tmp.inserted_id} not found")

    return serializeDict(update_user)


@user.put('/user/{id}/category', summary="유저 카테고리 정보 업데이트")
async def update_user_category(id, category: str, current_user: UserOut = Depends(get_current_user)):
    if not id == str(current_user["_id"]):
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN)

    user = db.user.find_one({"_id": ObjectId(current_user["_id"])})
    user["categories"][category] = int(user["categories"][category]) + 1
    db.user.find_one_and_update({"_id": ObjectId(id)}, {"$set": dict(user)})

    return 'OK'


@user.delete('/user/{id}', summary="유저 삭제")
async def delete_user(id, current_user: UserOut = Depends(get_current_user)):
    if not id == str(current_user["_id"]):
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN)

    user_folders = db.user.find_one({"_id": ObjectId(current_user["_id"])})["folders"]
    for user_folder in user_folders:
        folder = db.folder.find_one({"_id": ObjectId(user_folder["folder_id"])})
        if folder["shared"]:
            if len(folder["users"]) == 2:
                folder = db.folder.find_one_and_update(
                    {"_id": ObjectId(user_folder["folder_id"])},
                    {"$pull": {"users": {"email": current_user["email"]}}, "$set": {"shared": False}},
                    return_document=ReturnDocument.AFTER
                )
                db.user.find_one_and_update(
                    {"email": folder["users"][0]["email"], "folders.folder_id": user_folder["folder_id"]},
                    {"$set": {"folders.$.shared": False}},
                )
            else:
                db.folder.find_one_and_update(
                    {"_id": ObjectId(user_folder["folder_id"])},
                    {"$pull": {"users": {"email": current_user["email"]}}}
                )
        else:
            db.folder.find_one_and_delete({"_id": ObjectId(user_folder["folder_id"])})

    user = db.user.find_one_and_delete({"_id": ObjectId(current_user["_id"])})
    if user is not None:
        return HTTPException(status_code=status.HTTP_204_NO_CONTENT, detail=f"user deleted")
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"user {id} not found")