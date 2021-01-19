import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import axios from "axios";
import LocalStorageService from "./utils/LocalStorageService";

import "./index.css";
import "./styles/icons.css";

axios.defaults.baseURL = "http://localhost:8080/api/";
axios.defaults.headers.post["Content-Type"] = "application/json";

axios.interceptors.request.use(
  (config) => {
    const token = LocalStorageService.getAccessToken();
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    return response;
  },
  function (error) {
    const originalRequest = error.config;

    if (
      error.response.status === 403 &&
      originalRequest.url === "http://localhost:8080/api/auth/refresh"
    ) {
      window.location.url = "http://localhost:8080/login";
      return Promise.reject(error);
    }

    const refreshToken = LocalStorageService.getRefreshToken();

    if (
      error.response.status === 403 &&
      !originalRequest._retry &&
      refreshToken
    ) {
      originalRequest._retry = true;
      return axios
        .post("/auth/refresh", {
          refreshToken,
        })
        .then((res) => {
          if (res.status === 201) {
            LocalStorageService.setToken(res.data);
            return axios(originalRequest);
          }
        });
    }

    return Promise.reject(error);
  }
);

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
