from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from api.api_v1 import router as api_router


tags_metadata = [
    {
        "name": "token",
        "description": "토큰으로 유저 정보 확인",
    },
    {
        "name": "user",
        "description": "유저 CRUD. **회원가입** 포함",
    },
    {
        "name": "folder",
        "description": "폴더 CRUD (**폴더 자체)**",
    },
    {
        "name": "folder/user",
        "description": "폴더 user CUD (폴더 내의 **user 필드**)",
    },
    {
        "name": "folder/url",
        "description": "폴더 url 관련 CUD (폴더 url, tag)",
    },
    {
        "name": "memo",
        "description": "폴더의 url에 달리는 memo CRUD",
    },
    {
        "name": "recommend",
        "description": "유저의 category 성향에 맞는 url 추천",
    },
]

app = FastAPI(
    title="Project Urls",
    description="팀 이글아이 SSAFY 자율 프로젝트 Urls",
    version="0.0.1",
    openapi_tags=tags_metadata
)

origins = [
    "http://localhost",
    "https://localhost:8000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(api_router)



# uvicorn main:app --reload
# python -m venv venv
# source venv/Scripts/activate
# pip freeze > requirements.txt
# pip install -r requirements.txt
