from typing import List, Optional
from pydantic import BaseModel, EmailStr, HttpUrl, Field
from bson import ObjectId
from datetime import datetime 
from .common import PyObjectId


class MemoUser(BaseModel):
    email: EmailStr
    nickname : str
    avatar: HttpUrl


class Memo(BaseModel):
    id: PyObjectId = Field(alias='_id')
    user: MemoUser
    highlight: Optional[str]
    content: str
    created_at: datetime
    updated_at: datetime

    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }


class MemoIn(BaseModel):
    highlight: Optional[str]
    content: str


class MemoInDB(Memo):
    pass


class Memos(BaseModel):
    memos: List[Memo] = []

class MemosOut(Memos):
    id: PyObjectId = Field(alias='_id')
    
    class Config:
        arbitrary_types_allowed = True
        json_encoders = {
            ObjectId: str
        }