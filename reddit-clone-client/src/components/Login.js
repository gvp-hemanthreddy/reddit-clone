import React, { useState, useEffect } from "react";
import { Link, useHistory, Redirect } from "react-router-dom";
import axios from "axios";
import InputGroup from "./InputGroup";
import LocalStorageService from "../utils/LocalStorageService";
import { useAuthDispatch, useAuthState } from "../context/Auth";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const history = useHistory();
  const dispatch = useAuthDispatch();
  const { authenticated } = useAuthState();

  const errorsListToMap = (errorsList) => {
    const errorsMap = {};
    errorsList.forEach((error) => {
      errorsMap[error.field] = error.message;
    });
    setErrors(errorsMap);
  };

  const validateInput = () => {
    const errorsMap = {};
    if (username === "") errorsMap.username = "username is Empty";
    if (password === "") errorsMap.password = "Password is Empty";
    setErrors(errorsMap);
    return Object.keys(errorsMap).length === 0;
  };

  const formSubmit = async (event) => {
    event.preventDefault();

    if (!validateInput()) return;

    try {
      const response = await axios.post("auth/login", {
        username,
        password,
      });
      LocalStorageService.setToken(response.data);
      dispatch("LOGIN", response.data.username);
      history.push("/");
    } catch (err) {
      if (err.response?.data?.subErrors) {
        errorsListToMap(err.response.data.subErrors);
      } else {
        setErrors({ global: err.message });
      }
    }
  };

  useEffect(() => {
    document.title = "Login";
  }, []);

  if (authenticated) {
    return <Redirect to="/" />;
  }

  return (
    <div className="flex bg-white">
      <div
        className="h-screen bg-center bg-cover w-36"
        style={{ backgroundImage: "url('images/bricks.jpg')" }}
      ></div>
      <div className="flex flex-col justify-center pl-6">
        <div className="w-72">
          <h1 className="text-lg font-medium">Login</h1>
          <p className="mb-10 text-xs">
            By continuing, you agree to our User Agreement and Privacy Policy.
          </p>
          <form onSubmit={formSubmit}>
            <InputGroup
              type="text"
              placeholder="username"
              value={username}
              setValue={setUsername}
              error={errors.username}
            />
            <InputGroup
              type="password"
              placeholder="Password"
              value={password}
              setValue={setPassword}
              error={errors.password}
            />
            <small className="block mb-2 text-sm text-red-500">
              {errors.loginError}
            </small>
            <button className="w-full p-3 mb-4 text-xs font-bold text-white uppercase bg-blue-500 rounded hover:bg-blue-400">
              Login
            </button>
            <small>
              New to Reddit?
              <Link
                to="/register"
                className="ml-1 font-bold text-blue-500 uppercase"
              >
                Sign Up
              </Link>
            </small>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
