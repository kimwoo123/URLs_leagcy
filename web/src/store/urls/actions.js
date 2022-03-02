import { urls } from "src/api/index";
import { vueRouter } from "src/router";
import axios from "axios";

export async function GET_FOLDER({ commit }) {
  await urls.folderAll().then(async result => {
    const folders = result.data;
    await commit("setFolder", folders);
  });
}

export async function GET_ALL_URL({ commit }) {
  await urls.urlFindAll().then(async result => {
    const allFolderData = {
      _id: "",
      folder_name: "모든 Urls",
      urls: result.data,
      users: []
    };
    await commit("setUrl", result.data);
    await commit("setFolderNow", allFolderData);
  });
}

export async function GET_FOLDER_ULR({ commit }, folderId) {
  if (folderId !== undefined) {
    await urls
      .folderDetail(folderId)
      .then(async result => {
        await commit("setUrl", result.data.urls);
        await commit("setFolderNow", result.data);
      })
      .catch(async error => {
        console.log(error);
        await commit("setUrl", []);
        await commit("setFolderNow", {});
      });
  }
}

export async function CREATE_FOLDER({ commit, dispatch, getters }, folderData) {
  await urls.folderCreate(folderData).then(result => {
    dispatch("GET_FOLDER");
    commit("setUrl", result.data.urls);
    commit("setFolderNow", result.data);
    vueRouter.push({
      name: "MyFolder",
      params: { folder_id: getters.folderNow._id }
    });
  });
}

export async function PUT_FOLDER_NAME({ commit, dispatch }, folderData) {
  await urls.folderPut(folderData).then(async result => {
    dispatch("GET_FOLDER");
    commit("setFolderNow", result.data);
  });
}

export async function DELETE_FOLDER({ commit, dispatch }, folderId) {
  await urls.folderDelete(folderId).then(async result => {
    dispatch("GET_FOLDER");
    commit("setUrl", []);
    commit("setFolderNow", {});
  });
}

export function OPEN_MEMO({ commit }) {
  commit("openMemo");
}

export function CLOSE_MEMO({ commit }) {
  commit("closeMemo");
}

export async function GET_URL_MEMO({ commit }, memoId) {
  await urls.memoAll(memoId).then(async result => {
    commit("setSelectedMemoId", memoId);
    commit("setSelectedMemoTitle", result.data.url_title);
    commit("setUrlMemo", result.data.memos);
  });
}

export async function CREATE_URL_MEMO({ commit }, memoData) {
  await urls.memoCreate(memoData).then(async result => {
    commit("setUrlMemo", result.data.memos);
  });
}

export async function PUT_URL_MEMO({ commit }, memoData) {
  await urls
    .memoPut(memoData)
    .then(async result => {
      commit("setUrlMemo", result.data.memos);
    })
}

export async function DELETE_URL_MEMO({ commit }, memoData) {
  await urls.memoDelete(memoData).then(async result => {
    commit("setUrlMemo", result.data.memos);
  });
}

export async function GET_FOLDER_URL_SEARCH({ commit }, urlData) {
  if (urlData.folder_id == "") {
  } else {
    await urls.urlFindFolder(urlData).then(async result => {
      commit("setSearchData", result.data);
    });
  }
}

export async function PUT_URL_TAG({ commit, dispatch }, payload) {
  await urls.urlPut(payload.folderId, payload.data).then(result => {
    dispatch("GET_FOLDER_ULR", payload.folderId);
  });
}

export async function DELETE_URL_SEARCH({ commit }) {
  commit("setSearchData", []);
}

export async function CREATE_URL({ commit }, urlData) {
  await urls.urlCreate(urlData.folder_id, urlData).then(async result => {
    localStorage.setItem("needRecommend", true);
    commit("setFolderNow", result.data);
    commit("setUrl", result.data.urls);
    commit("recommend/setRecommendLoading", null, { root: true });
  });
}

export function ADD_WILL_DELETE_URL({ commit }, data) {
  commit("addWillDeleteURL", data);
}

export function DELETE_WILL_DELETE_URL({ commit }, data) {
  commit("deleteWillDeleteURL", data);
}

export async function DELETE_URL({ commit }, urlData) {
  await urls
    .urlDelete(urlData)
    .then(result => {
      commit("deleteWillDeleteURL", urlData);
    })
    .catch(err => {
      commit("deleteWillDeleteURL", urlData);
    });
}

export async function DELETE_URL_BY_TIMER(
  { commit, dispatch, getters },
  urlData
) {
  const folderId = getters.folderNow._id;
  await urls
    .urlDelete(urlData)
    .then(result => {
      commit("deleteWillDeleteURL", urlData);
      if (folderId !== "") {
        commit("setFolderNow", result.data);
      } else {
        dispatch("GET_ALL_URL");
      }
    })
    .catch(err => {
      commit("deleteWillDeleteURL", urlData);
    });
}

export async function ADD_FOLDER_USER({ commit, dispatch }, folderUserData) {
  await urls.folderCreateUser(folderUserData).then(async result => {
    dispatch("GET_FOLDER");
    commit("setFolderNow", result.data);
  });
}

export async function PUT_FOLDER_USER({ commit, dispatch }, folderUserData) {
  await urls.folderPutUser(folderUserData).then(async result => {
    dispatch("GET_FOLDER");
    commit("setFolderNow", result.data);
  });
}

export async function DELETE_FOLDER_USER({ commit, dispatch }, folderUserData) {
  await urls.folderDeleteUser(folderUserData).then(async result => {
    dispatch("GET_FOLDER");
    commit("setFolderNow", result.data);
  });
}

export async function LEAVE_FOLDER({ commit, dispatch }, folderData) {
  await urls.leaveFolder(folderData.folderId)
  .then(() => {
    dispatch("GET_FOLDER");
    vueRouter.push({
      name: "AllUrls",
      params: { id: folderData.userId }
    });
  })
}
