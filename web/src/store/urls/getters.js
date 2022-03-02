export function folderUrls(state) {
  return state.urls;
}

export function isUrlMemoOpen(state) {
  return state.urlMemoOpen;
}

export function folderNow(state) {
  return state.folderNow;
}

export function permissionNow(state, getters, rootState, rootGetters) {
  if (getters.folderNow._id === "") {
    return 2;
  } else {
    const myEmail = rootState.user.useremail;

    if (getters.folderNow.users) {
      const result = getters.folderNow.users.find(x => x.email === myEmail);
      if (result.permission == undefined) {
        return -1;
      } else {
        return result.permission;
      }
    } else {
      return 2;
    }
  }
}

export function willDeleteURL(state) {
  return state.willDeleteURL;
}

export function searchData(state) {
  return state.searchData;
}
