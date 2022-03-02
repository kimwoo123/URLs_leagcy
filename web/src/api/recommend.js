import { api } from "src/boot/axios";

// 해당 url의 tags 추천
export function recommendUrlTags(recommendData) {
  return api.get(
    `/tags?url=${recommendData.url}&count=${recommendData.count}`,
    recommendData
  );
}

// urls 추천 (해당 url 누적 수가 3 이상인 것만 대상)
export function recommendUrlGet(count) {
  return api.get(`/recommend?count=${count}`);
}

// 추천을 위한 전체 url DB 속 단일 URL 수정
export function recommendUrlPut(urlData) {
  return api.put("/recommend", urlData);
}

// 추천을 위한 전체 url DB에 신규 url 추가
export function recommendUrlCreate(urlData) {
  return api.post(`/recommend?url=${urlData.url}&category=${urlData.categoryName}`);
}

// url 추천
export function recommendUrl(data, id) {
  return api.get(`/recommend/${id}`, data);
}
