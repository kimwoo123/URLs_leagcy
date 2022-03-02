from fastapi import Depends, APIRouter, HTTPException, status
from fastapi.security import OAuth2AuthorizationCodeBearer
from datetime import datetime, timedelta
from typing import Optional
from jose import jwt, JWTError
from google.auth import jwt as google_jwt
from passlib.context import CryptContext
from models.user import UserOut
from config.db import db
from fastapi import Security
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer

SECRET_KEY = "4ea60b27e862df027b7cc1e76c2446bca8406b8b4db2d79f0c9d19ab188de9b1"
ALGORITHM = "RS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
# oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")
security = HTTPBearer()

token = APIRouter()


# def create_access_token(data: dict, expires_delta: Optional[timedelta] = None):
#     to_encode = data.copy()
#     if expires_delta:
#         expire = datetime.utcnow() + expires_delta
#     else:
#         expire = datetime.utcnow() + timedelta(minutes=15)
#     to_encode.update({"exp": expire})
#     encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
#     return encoded_jwt


async def get_current_user(credentials: HTTPAuthorizationCredentials = Security(security)):
    token = credentials.credentials

    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = google_jwt.decode(token, verify=False)
        email: str = payload.get("email")
        if email is None:
            raise credentials_exception
    except JWTError:
        raise credentials_exception
    user = db.user.find_one({"email": email})
    if user is None:
        raise credentials_exception
    return user

# OAuth2PasswordRequestForm 로 로그인
# @token.post("/token", summary="로그인 & 토큰 발급")
# async def login_for_access_token(form_data: OAuth2PasswordRequestForm = Depends()):
#     # 유저 확인
#     user = db.user.find_one({"email": form_data.username})
#     if not user:
#         raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="user email not existed", headers={"WWW-Authenticate": "Bearer"})
#     if not pwd_context.verify(form_data.password, user["password"]):
#         # pwd_context.verify(plain_password, hashed_password)
#         raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Password is not correct", headers={"WWW-Authenticate": "Bearer"})

#     # 토큰 생성
#     access_token_expires = timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
#     access_token = create_access_token(
#         data={"email": user["email"]}, expires_delta=access_token_expires
#     )
#     return {"access_token": access_token, "token_type": "bearer"}


@token.get("/token/me", response_model=UserOut, summary="토큰 유저 확인")
async def read_users_me(current_user: UserOut = Depends(get_current_user)):
    return current_user
