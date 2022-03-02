import {
  getUserFromCookie,
  getAuthFromCookie,
  getUseridFromCookie,
  getUserAvatarFromCookie,
  getUserEmailFromCookie
} from "src/utils/cookies";

export default function() {
  return {
    username: getUserFromCookie() || "",
    useremail: getUserEmailFromCookie() || "",
    token: getAuthFromCookie() || "",
    userid: getUseridFromCookie() || "",
    avatar: getUserAvatarFromCookie() || "",
    categories: localStorage.getItem("categories") || {}
  };
}
