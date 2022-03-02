from typing import List, Dict
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from .common import PyObjectId


CategoryItem = {
    "프론트": 0,
    "백엔드": 0,
    "모바일": 0,
    "빅데이터/AI": 0,
    "CS": 0,
    "DevOps": 0,
    "Tools(생산성)": 0,
    "기획/디자인": 0,
}

class UserTag(BaseModel):
    tag_name: str
    count: int


class UserFolder(BaseModel):
    id: PyObjectId = Field(alias='folder_id')
    folder_name: str
    shared: bool

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class UserIn(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl

    class Config:
        schema_extra = {
            "example": {
                "email": "ssafy@ssafy.com",
                "avatar": "https://via.placeholder.com/200.jpg",
                "nickname": "ssafy",
            }
        }


class UserInDB(UserIn):
    tags: List[UserTag] = []
    folders: List[UserFolder] = []
    categories: Dict = CategoryItem
    

class UserOut(UserInDB):
    id: PyObjectId = Field(alias='_id')

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }
