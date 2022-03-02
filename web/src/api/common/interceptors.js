import { vuexStore } from 'src/store/index'

export function setInterceptors(instance) {

  instance.interceptors.request.use(
    function(config) {
      // Do something before request is sent
      const accessToken = vuexStore.state.user.token;
      config.headers.Authorization = 'Bearer ' + accessToken;
      return config;
    },
    function(error) {
      // Do something with request error
      return Promise.reject(error);
    },
  );

  instance.interceptors.response.use(
    function(response) {
      // Any status code that lie within the range of 2xx cause this function to trigger
      // Do something with response data
      return response;
    },
    function(error) {
      // Any status codes that falls outside the range of 2xx cause this function to trigger
      // Do something with response error
      return Promise.reject(error);
    },
  );
  return instance;
}