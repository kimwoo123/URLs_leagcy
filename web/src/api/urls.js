import { api } from "src/boot/axios";

// 내 모든 폴더 조회
export function folderAll() {
  return api.get("/folder/me");
}

// 폴더 상세 조회
export function folderDetail(folderId) {
  return api.get(`/folder/${folderId}`);
}

// 폴더 명 변경
export function folderPut(folderData) {
  return api.put(`/folder/${folderData.id}`, folderData);
}

// 폴더 삭제
export function folderDelete(folderId) {
  return api.delete(`/folder/${folderId}`);
}

// 단일 폴더 생성
export function folderCreate(folderData) {
  return api.post("/folder", folderData);
}

// 폴더 유저 권한 변경
export function folderPutUser(folderUserData) {
  const userData = {
    email: folderUserData.email,
    permission: folderUserData.permission
  };
  return api.put(`/folder/${folderUserData.folder_id}/user`, userData);
}

// 폴더 유저 생성
export function folderCreateUser(folderUserData) {
  const userData = {
    email: folderUserData.email,
    permission: folderUserData.permission
  };
  return api.post(`/folder/${folderUserData.folder_id}/user`, userData);
}

// 폴더 유저 삭제
export function folderDeleteUser(folderUserData) {
  return api.delete(
    `/folder/${folderUserData.folder_id}/user?email=${folderUserData.email}`
  );
}

// 내 모든 폴더에서 내가 작성한 url 검색
export function urlFindAll() {
  return api.get("/folder/url/me");
}

// 폴더 내 특정 url 검색
export function urlFindFolder(urlData) {
  return api.get(`/folder/${urlData.folder_id}/url?pattern=${urlData.pattern}`);
}

// 폴더 내 특정 url 찾아, 해당 썸네일, 태그 수정
export function urlPut(folderId, urlData) {
  return api.put(`/folder/${folderId}/url`, urlData);
}

// 폴더 내 새로운 url 생성
export function urlCreate(folderId, urlData) {
  return api.post(`/folder/${folderId}/url`, urlData);
}

// 폴더 내 특정 url 삭제
export function urlDelete(urlData) {
  const data = {
    url: urlData.url
  };
  return api.put(`/folder/${urlData.folder_id}/url/delete`, data);
}

// url에 달린 모든 메모 조회
export function memoAll(memoId) {
  return api.get(`/memo/${memoId}`);
}

// url memos 리스트에 memo 생성
export function memoCreate(memoData) {
  return api.post(`/memo/${memoData.memos_id}`, memoData);
}

// url memos 리스트에 memo 수정
export function memoPut(memoData) {
  const data = {
    content: memoData.content
  };
  return api.put(`/memo/${memoData.memos_id}/${memoData.memo_id}`, data);
}

// url memos 리스트에 memo 삭제
export function memoDelete(memoData) {
  return api.delete(`/memo/${memoData.memos_id}/${memoData.memo_id}`);
}

export function leaveFolder(folderId) {
  return api.delete(`/folder/${folderId}/me`)
}