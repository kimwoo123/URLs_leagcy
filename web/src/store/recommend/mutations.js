export function setTag(state, result) {
  state.tag = result;
}

export function setRecommendTag(state, result) {
  state.recommendTag = result;
}

export function resetRecommendTag(state) {
  state.recommendTag = [];
}

export function setRecommendUrls(state, urls) {
  state.recommendUrls = urls;
  state.recommendLoading = true;
}

export function setSearchResult(state, urls) {
  state.searchResult = urls;
}

export function changeSearchMode(state) {
  state.searchMode = !state.searchMode;
}

export function setRecommendLoading(state) {
  state.recommendLoading = false
}