import { search } from "src/api/index";
import { recommend, auth } from "src/api/index";

export async function SEARCH_TAG({ commit }, queryData) {
  await search.searchTag(queryData).then(async result => {
    await commit("setSearchResult", result.data);
  });
}

export async function DELETE_TAG_SEARCH_RESULT({ commit }) {
  commit("setSearchResult", []);
}

export async function RECOMMEND_TAG({ commit }, recommendData) {
  await search.recommendTag(recommendData).then(async result => {
    // if (!Object.keys(result.data).includes('status_code')) {
    // }
    recommendData.tags = result.data;
    await commit("setRecommendTag", result.data);
  });
}

export async function DELETE_RECOMMEND_TAG({ commit }) {
  await commit("resetRecommendTag");
}

export async function GET_RECOMMEND_URL({ commit }, count) {
  await recommend.recommendUrlGet(count).then(result => {
    commit("setRecommendUrls", result.data);
  });
}

export async function PUT_USER_CATEGORY({ commit }, payload) {
  await auth.userCatergoryUpdate(payload);
}

export async function POST_URL_RECOMMEND({ commit }, payload) {
  await recommend.recommendUrlCreate(payload);
}

export function CHANGE_SEARCH_MODE({ commit }) {
  commit("changeSearchMode");
}
