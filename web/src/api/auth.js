import { api } from "src/boot/axios";

export function getUserInfo(userId) {
  return api.get(`/user/${userId}`);
}

export function user(loginData) {
  return api.post("/user", loginData);
}

export function tokenMe() {
  return api.get("/token/me");
}

export function deleteUser(userId) {
  return api.delete(`/user/${userId}`);
}

export function userCatergoryUpdate(payload) {
  return api.put(`/user/${payload.userid}/category?category=${payload.category}`)
}