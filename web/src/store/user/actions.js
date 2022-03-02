import { auth } from "src/api/index";
import {
  saveAuthToCookie,
  saveUserToCookie,
  saveUseridToCookie,
  saveUserAvatarToCookie,
  saveUserEmailToCookie,
  deleteCookie
} from "src/utils/cookies";

export async function LOGIN({ commit }, userData) {
  const loginData = {
    email: userData.useremail,
    nickname: userData.nickname,
    avatar: userData.avatar
  };
  commit("setToken", userData.token);
  commit("setUserEmail", userData.useremail);
  commit("setUserName", userData.nickname);
  commit("setUserAvatar", userData.avatar);
  saveAuthToCookie(userData.token);
  saveUserEmailToCookie(userData.useremail);
  saveUserToCookie(userData.nickname);
  saveUserAvatarToCookie(userData.avatar);
  const { data } = await auth.user(loginData);
  commit("setUserid", data["_id"]);
  saveUseridToCookie(data["_id"]);
}

export async function LOGOUT({ commit }) {
  commit("clearUserCategories");
  commit("clearUsername");
  commit("clearToken");
  commit("clearUserid");
  commit("clearUserEmail");
  commit("clearUserAvatar");
  commit("urls/cloeseMemo", null, { root: true });
  deleteCookie("til_auth");
  deleteCookie("til_user");
  deleteCookie("til_userid");
  deleteCookie("til_avatar");
  deleteCookie("til_user_email");
  localStorage.removeItem('vuex')
  localStorage.removeItem('needRecommend')
}

export async function GET_USER_INFO({ commit }, userId) {
  await auth.getUserInfo(userId).then(async result => {
    commit("getUserCategories", result.data.categories);
  });
}

export async function UPDATE_USER_INFO({ commit }, loginData) {
  await auth.user(loginData).then(async result => {
    deleteCookie("til_user");
    deleteCookie("til_avatar");
    commit("clearUsername");
    commit("clearUserAvatar");

    saveUserToCookie(result.data.nickname);
    saveUserAvatarToCookie(result.data.avatar);
    commit("setUserName", result.data.nickname);
    commit("setUserAvatar", result.data.avatar);
  });
}

export async function DELETE_USER({ commit }, userId) {
  await auth.deleteUser(userId).then(async result => {
    commit("clearUserCategories");
    commit("clearUsername");
    commit("clearToken");
    commit("clearUserid");
    commit("clearUserEmail");
    commit("clearUserAvatar");
    deleteCookie("til_auth");
    deleteCookie("til_user");
    deleteCookie("til_userid");
    deleteCookie("til_avatar");
    deleteCookie("til_user_email");
  });
}
