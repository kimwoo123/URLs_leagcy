from fastapi import Depends, APIRouter, HTTPException, status
from models.folder import User, UrlIn, UrlInDB
from models.memo import Memos
from config.db import db
from serializers.common import serializeDict, serializeList, serializeList_folder
from fastapi.encoders import jsonable_encoder
# from fastapi.responses import JSONResponse
from bson import ObjectId
from pymongo import ReturnDocument
from .token import get_current_user
from urllib.request import urlopen
from bs4 import BeautifulSoup
from pprint import pprint

folder_url = APIRouter()


async def tag_count_increase(increase_tags, user_id=None, user_email=None):
    if user_id is not None:
        user_find_key, user_find_value = "_id", ObjectId(user_id)
    else:
        user_find_key, user_find_value = "email", user_email

    user = db.user.find_one({user_find_key: user_find_value})
    user_tags = [item["tag_name"] for item in user["tags"]]
    for tag in increase_tags:
        if tag in user_tags:
            db.user.find_one_and_update(
                {user_find_key: user_find_value, "tags.tag_name": tag},
                {"$inc": {"tags.$.count": 1}}
            )
        else:
            db.user.find_one_and_update(
                {user_find_key: user_find_value},
                {"$push": {"tags": {"tag_name": tag, "count": 1}}}
            )

async def tag_count_decrease(decrease_tags, user_id=None, user_email=None):
    if user_id is not None:
        user_find_key, user_find_value = "_id", ObjectId(user_id)
    else:
        user_find_key, user_find_value = "email", user_email

    db.user.find_one({user_find_key: user_find_value})
    for tag in decrease_tags:
        db.user.find_one_and_update(
            {user_find_key: user_find_value, "tags.tag_name": tag},
            {"$inc": {"tags.$.count": -1}}
        )
    db.user.find_one_and_update(
        {user_find_key: user_find_value},
        {"$pull": {"tags": {"count": 0}}}
    )


@folder_url.get('/folder/url/me', summary="내 모든 폴더에서 내가 작성한 url 검색")
async def find_all_folder_url_me(user: User = Depends(get_current_user)):
    folders = db.folder.aggregate([
        {"$match": {"urls.added_by.email": user["email"]}},
        {"$project": {
            "urls": {"$filter": {
                "input": "$urls",
                "as": "elem",
                "cond": { "$eq": ["$$elem.added_by.email", user["email"]] }
                }
            }}
        }
    ])
    if folders is not None:
        result = []
        for folder in folders:
            for url in folder["urls"]:
                url["folder_id"] = folder["_id"]
                url["folder_name"] = db.folder.find_one({"_id": ObjectId(folder["_id"])})["folder_name"]
                url["memos_count"] = len(db.memo.find_one({"_id": ObjectId(url["memos_id"])})["memos"])
                result.append(url)
        result.sort(key=lambda x: ObjectId(x["memos_id"]).generation_time, reverse=True)
        return serializeList_folder(result)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND)


@folder_url.get('/folder/url/me/search', summary="내 url들에서 특정 단어 포함 url 검색")
async def find_all_folder_url_me(pattern, user: User = Depends(get_current_user)):
    folders = db.folder.aggregate([
        {"$match": {"urls.added_by.email": user["email"]}},
        {"$project": {
            "urls": {"$filter": {
                "input": "$urls",
                "as": "elem",
                "cond": {
                    "$and": [
                        { "$regexMatch": { "input": "$$elem.url" , "regex": pattern } },
                        { "$eq": ["$$elem.added_by.email", user["email"]] }
                    ]
                }
                }
            }}
        }
    ])
    if folders is not None:
        result = []
        for folder in folders:
            for url in folder["urls"]:
                url["folder_id"] = folder["_id"]
                url["folder_name"] = db.folder.find_one({"_id": ObjectId(folder["_id"])})["folder_name"]
                url["memos_count"] = len(db.memo.find_one({"_id": ObjectId(url["memos_id"])})["memos"])
                result.append(url)
        result.sort(key=lambda x: ObjectId(x["memos_id"]).generation_time, reverse=True)

        return serializeList_folder(result)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND)


@folder_url.get('/folder/{folder_id}/url', summary="폴더 내에서 특정 단어 포함 url 검색")
async def find_one_folder_url(folder_id, pattern):
    folder = db.folder.aggregate([
        {"$match": {"_id": ObjectId(folder_id)}},
        {"$project": {
            "urls": {"$filter": {
                "input": "$urls",
                "as": "elem",
                "cond": { "$regexMatch": { "input": "$$elem.url" , "regex": pattern }}
            }}
        }}
    ])
    if folder is not None:
        folder = serializeList(folder)[0]
        for idx, url in enumerate(folder["urls"]):
            memo = db.memo.find_one({"_id": ObjectId(folder["urls"][idx]["memos_id"])})
            folder["urls"][idx]["memos_count"] = len(memo["memos"])
        return [folder]
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_url.post('/folder/{folder_id}/url', summary="폴더 내 새로운 url 생성")
async def create_folder_url(folder_id, url_in: UrlIn, current_user: User = Depends(get_current_user)):
    if db.folder.find_one({"_id": ObjectId(folder_id), "urls.url": url_in.url}):
        raise HTTPException(status_code=status.HTTP_409_CONFLICT)
    
    await tag_count_increase(url_in.tags, user_id=current_user["_id"])
    
    tmp = db.memo.insert_one(jsonable_encoder(Memos()))
    try:
        html = urlopen(url_in.url)
        bsObject = BeautifulSoup(html, "html.parser")
        title = bsObject.head.title.text
        og_image = bsObject.head.find("meta", {"property": "og:image"}).get("content")
        url = UrlInDB(
            **url_in.dict(), 
            added_by=User(**current_user),
            title=title,
            thumbnail=og_image,
            memos_id=tmp.inserted_id
        )
    except:
        url = UrlInDB(
            **url_in.dict(), 
            added_by=User(**current_user),
            memos_id=tmp.inserted_id
        )

    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)}, {"$push": {"urls": jsonable_encoder(url)}},
        return_document=ReturnDocument.AFTER
    )

    if folder is not None:
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")


@folder_url.put('/folder/{folder_id}/url', summary="폴더 내 특정 url을 찾아, 해당 url의 태그 수정")
async def update_folder_url(folder_id, url_in: UrlIn, current_user: User = Depends(get_current_user)):
    if db.folder.find_one({"_id": ObjectId(folder_id), "users.email": current_user["email"]}):
        old_folder = db.folder.find_one({"_id": ObjectId(folder_id), "urls.url": url_in.url}, {"urls.$":1})
        folder = db.folder.find_one_and_update(
            {"_id": ObjectId(folder_id), "urls.url": url_in.url}, 
            {"$set": {"urls.$.tags": url_in.tags}},
            return_document=ReturnDocument.AFTER
        )
        if folder is not None:
            # 기존에 있던 태그 count 감소
            await tag_count_decrease(old_folder["urls"][0]["tags"], user_id=current_user["_id"])
            # 새로 생기는 태그 count 증가
            await tag_count_increase(url_in.tags, user_id=current_user["_id"])
            
            folder["urls"] = folder["urls"][::-1]
            return serializeDict(folder)
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"url is not in the folder")
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder is not in my folder")


@folder_url.put('/folder/{folder_id}/url/delete', summary="폴더 내 특정 url 삭제")
async def delete_folder_url(folder_id, url_in: UrlIn, current_user: User = Depends(get_current_user)):
    tmp = db.folder.find_one(
        {"_id": ObjectId(folder_id), "urls.url": url_in.url},
        {"urls.$":1}
    )
    if tmp is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {url_in.url} not found")

    memos_id = tmp["urls"][0]["memos_id"]
    await tag_count_decrease(tmp["urls"][0]["tags"], user_id=current_user["_id"])
    
    folder = db.folder.find_one_and_update(
        {"_id": ObjectId(folder_id)},
        {"$pull": {"urls": {"url": url_in.url}}}, 
        return_document=ReturnDocument.AFTER
    )
    db.memo.delete_one({"_id": ObjectId(memos_id)})
    
    if folder is not None:
        folder["urls"] = folder["urls"][::-1]
        return serializeDict(folder)
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"folder {folder_id} not found")

