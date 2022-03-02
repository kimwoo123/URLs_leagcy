from fastapi import APIRouter

other = APIRouter()

@other.get('/other')
async def notifications():
    return [
        {
            'version': 'v0.0.7',
            'date': '2021-11-16',
            'title': '크롬 익스텐션 하이라이트 업데이트',
            'description': '이제 하이라이트를 작성할 수 있습니다.'
        },
        {
            'version': 'v0.0.6',
            'date': '2021-11-15',
            'title': '크롬 익스텐션 업데이트',
            'description': '크롬 익스텐션을 업데이트 하였습니다.'
        },
        {
            'version': 'v0.0.5',
            'date': '2021-11-12',
            'title': '정식으로 오픈합니다.',
            'description': 'Urls 가 정식으로 오픈했습니다.'
        },
        {
            'version': 'v0.0.4',
            'date': '2021-11-10',
            'title': '정식으로 CI/CD 를 적용했습니다.',
            'description': 'CI/CD 를 통하여 자동화했습니다.'
        },
        {
            'version': 'v0.0.3',
            'date': '2021-11-08',
            'title': 'AI를 도입하였습니다. ',
            'description': '조금 더 좋은 태그를 사용해보세요'
        },
        {
            'version': 'v0.0.2',
            'date': '2021-11-06',
            'title': '정식으로 오픈합니다.',
            'description': 'Urls 가 정식으로 오픈했습니다.'
        },
        {
            'version': 'v0.0.1',
            'date': '2021-11-04',
            'title': '정식으로 엘라스틱 서치를 붙였습니다.',
            'description': '조금 더 향상된 검색을 할 수 있습니다.'
        },
    ]
@other.get('/other/folders')
async def folders():
    return [
        {
        "folder_id": "hello1",
        "folder_name": "folder1",
        "shared": 'false'
        },
        {
        "folder_id": "hello2",
        "folder_name": "folder1",
        "shared": 'false'
        },
        {
        "folder_id": "hello3",
        "folder_name": "folder1",
        "shared": 'false'
        },
        {
        "folder_id": "hello4",
        "folder_name": "folder1",
        "shared": 'false'
        }
    ]
