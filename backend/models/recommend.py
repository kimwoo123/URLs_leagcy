from typing import Dict
from pydantic import BaseModel, HttpUrl
from models.user import CategoryItem


class Recommend(BaseModel):
    url: HttpUrl
    categories: Dict = CategoryItem
    count: int = 1


class UrlIn(Recommend):

    class Config:
        schema_extra = {
            "example": {
                "url": "https://www.google.com",
                "categories": {
                    "프론트": 0,
                    "백엔드": 0,
                    "모바일": 0,
                    "빅데이터/AI": 0,
                    "CS": 0,
                    "DevOps": 0,
                    "Tools(생산성)": 0,
                    "기획/디자인": 0,
                }
            }
        }