export function setUserName(state, userName) {
  state.username = userName;
}

export function clearUsername(state) {
  state.username = "";
}

export function setToken(state, token) {
  state.token = token;
}

export function clearToken(state) {
  state.token = "";
}

export function setUserid(state, userid) {
  state.userid = userid;
}

export function clearUserid(state) {
  state.userid = "";
}

export function setUserAvatar(state, avatar) {
  state.avatar = avatar;
}

export function clearUserAvatar(state) {
  state.avatar = "";
}

export function setUserEmail(state, useremail) {
  state.useremail = useremail;
}

export function clearUserEmail(state) {
  state.useremail = "";
}

export function getUserCategories(state, categories) {
  localStorage.setItem("categories", JSON.stringify(categories));
  state.categories = categories;
}

export function clearUserCategories(state) {
  localStorage.clear();
  state.categories = {};
}
