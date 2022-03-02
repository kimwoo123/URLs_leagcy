from fastapi import APIRouter

from routes.other import other
from routes.user import user
from routes.token import token
from routes.folder import folder 
from routes.folder_url import folder_url 
from routes.folder_user import folder_user 
from routes.memo import memo
from routes.recommend import recommend
from routes.search import search

router = APIRouter()

router.include_router(token, tags=["token"])
router.include_router(user, tags=["user"])
router.include_router(folder, tags=["folder"])
router.include_router(folder_user, tags=["folder/user"])
router.include_router(folder_url, tags=["folder/url"])
router.include_router(memo, tags=["memo"])
router.include_router(recommend, tags=["recommend"])
router.include_router(search, tags=["search"])
router.include_router(other, tags=["other"])

