from elasticsearch import Elasticsearch
from fastapi import APIRouter
from config.db import db
from bson import ObjectId

search = APIRouter()
es = Elasticsearch(hosts="elastic:k5b201eagle@k5b201.p.ssafy.io", port=9200)

@search.get('/search', summary="검색")
async def search_tag(searchText: str, folder: str):
  print(searchText, folder)
  res = es.search(
    index="folder_v14",
    body=
    {
      "_source": "false",
      "query": {
        "nested": {
          "path": "urls",
          "inner_hits": {},
          "query": {
            "bool": {
              "must": {
                "match": { 
                  "urls.tags": { 
                    "query": searchText,
                    "fuzziness": "AUTO"
                    }
                  },
              },
              "filter": [
                {
                  "match": { "_id" : folder }
                }
              ]
            }
          }
        }
      }
    }
  )
  try:
    result = []
    for url in res['hits']['hits'][0]['inner_hits']['urls']['hits']['hits']:
        url["_source"]["memos_count"] = len(db.memo.find_one({"_id": ObjectId(url["_source"]["memos_id"])})["memos"])
        result.append(url)
    return result
  except:
    return '검색 결과가 없습니다'
