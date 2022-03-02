from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId

class User(BaseModel):
    email: EmailStr
    nickname: str
    avatar: HttpUrl
    permission: int = Field(0, ge=0, le=2, description ="0: 읽기 가능, 1: 편집 가능(+url, 메모, 유저 추가 가능), 2: 소유자")


class UserIn(BaseModel):
    email: EmailStr
    permission: int = Field(0, ge=0, le=2, description ="0: 읽기 가능, 1: 편집 가능(+url, 메모, 유저 추가 가능), 2: 소유자")
    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "permission": 0
            }
        }



class UrlIn(BaseModel):
    url: HttpUrl
    tags: List[str] = []
    class Config:
        schema_extra = {
            "example": {
                "url": "https://www.naver.com/",
                "tags": [],
            }
        }


class UrlInDB(UrlIn):
    title: Optional[str] = None
    thumbnail: Optional[HttpUrl] = None
    added_by: User
    memos_id: PyObjectId
    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class Url(UrlInDB):
    pass



class FolderIn(BaseModel):
    folder_name: str
    class Config:
        schema_extra = {
            "example": {
                "folder_name": "folder1",
            }
        }


class FolderInDB(BaseModel):
    folder_name: str
    shared: bool = False
    users: List[User] = []
    urls: List[Url] = []


class FolderOut(FolderInDB):
    id: PyObjectId = Field(alias='_id')

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }