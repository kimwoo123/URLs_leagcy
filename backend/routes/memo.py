from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User
from models.memo import MemoIn, MemoInDB, MemoUser, MemosOut
from config.db import db
from serializers.common import serializeDict, serializeList
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pymongo import ReturnDocument
from .token import get_current_user
from datetime import datetime
from pytz import timezone


KST = timezone('Asia/Seoul')
memo = APIRouter()


@memo.get('/memo/{memos_id}', summary="url에 달린 모든 메모(memos) 조회")
async def find_folder_url_memo(memos_id):
    memos = db.memo.find_one({"_id": ObjectId(memos_id)})
    if memos is not None:
        folder_urls = db.folder.find_one({"urls.memos_id": str(memos["_id"])}, {"urls.$": 1})
        if folder_urls is not None:
            memos["url_title"] = folder_urls["urls"][0]["title"]
            return serializeDict(memos)
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"url title not found")
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")


@memo.post('/memo/{memos_id}', summary="url memos 리스트에 memo 생성")
async def create_folder_url_memo(memos_id, memo_in: MemoIn, user: User = Depends(get_current_user)):
    now_time = datetime.now().astimezone(KST).strftime('%Y-%m-%d %H:%M:%S')
    memo_inDB = MemoInDB(
        **memo_in.dict(),
        _id = ObjectId(),
        user = MemoUser(**user),
        created_at = now_time,
        updated_at = now_time
    )
    memo = db.memo.find_one_and_update(
        {"_id": ObjectId(memos_id)}, {"$push": {"memos": jsonable_encoder(memo_inDB)}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")


@memo.put('/memo/{memos_id}/{memo_id}', summary="url memos 리스트에 있는 memo 수정")
async def update_folder_url_memo(memos_id, memo_id, memo_in: MemoIn, user: User = Depends(get_current_user)):
    now_time = datetime.now().astimezone(KST).strftime('%Y-%m-%d %H:%M:%S')
    memo = db.memo.find_one_and_update(
        {"_id": ObjectId(memos_id), "memos._id": memo_id}, 
        {"$set": {"memos.$.highlight": memo_in.highlight, "memos.$.content": memo_in.content, "memos.$.updated_at": now_time}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")

@memo.delete('/memo/{memos_id}/{memo_id}', summary="url memos 리스트에 있는 memo 삭제")
async def delete_folder_url_memo(memos_id, memo_id):
    memo = db.memo.find_one_and_update(
        {"_id": ObjectId(memos_id)}, 
        {"$pull": {"memos": {"_id": memo_id}}},
        return_document=ReturnDocument.AFTER
    )
    if memo is not None:
        return serializeDict(memo)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"memo {memos_id} not found")