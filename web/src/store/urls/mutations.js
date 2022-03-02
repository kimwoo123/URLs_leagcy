export function setFolder(state, folders) {
  state.folders = folders;
}

export function setFolderNow(state, folder) {
  state.folderNow = folder;
}

export function setUrl(state, urls) {
  state.urls = urls;
}

export function openMemo(state) {
  state.urlMemoOpen = true;
}

export function closeMemo(state) {
  state.urlMemoOpen = false;
}

export function setUrlMemo(state, memos) {
  state.memos = memos;
}

export function setSelectedMemoId(state, memoId) {
  state.selectedMemoId = memoId;
}

export function setSelectedMemoTitle(state, memoTitle) {
  state.selectedMemoTitle = memoTitle;
}

export function setSearchData(state, data) {
  state.searchData = data;
}

export function addWillDeleteURL(state, data) {
  state.willDeleteURL.push(data)
}

export function deleteWillDeleteURL(state, data) {
  const filltered = state.willDeleteURL.filter((x) => x.memos_id !== data.memos_id)
  state.willDeleteURL = filltered
}
