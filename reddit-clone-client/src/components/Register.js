import React, { useState, useEffect } from "react";
import { Link, useHistory, Redirect } from "react-router-dom";
import axios from "axios";
import InputGroup from "./InputGroup";
import { useAuthState } from "../context/Auth";

function Register() {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [agreement, setAgreement] = useState(false);
  const [errors, setErrors] = useState({});
  const history = useHistory();
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
    if (!agreement) errorsMap.agreement = "You must agree to T&Cs";
    if (email === "") errorsMap.email = "Email is Empty";
    if (username === "") errorsMap.username = "username is Empty";
    if (password === "") errorsMap.password = "Password is Empty";
    setErrors(errorsMap);
    return Object.keys(errorsMap).length === 0;
  };

  const formSubmit = async (event) => {
    event.preventDefault();

    if (!validateInput()) return;

    try {
      await axios.post("auth/signup", {
        username,
        email,
        password,
      });
      history.push("/login");
    } catch (err) {
      if (err.response?.data?.subErrors) {
        errorsListToMap(err.response.data.subErrors);
      } else {
        setErrors({ global: err.message });
      }
    }
  };

  useEffect(() => {
    document.title = "Register";
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
          <h1 className="text-lg font-medium">Sign Up</h1>
          <p className="mb-10 text-xs">
            By continuing, you agree to our User Agreement and Privacy Policy.
          </p>
          <form onSubmit={formSubmit}>
            <div className="mb-6">
              <input
                type="checkbox"
                className="mr-1 cursor-pointer"
                id="agreement"
                checked={agreement}
                onChange={(e) => setAgreement(e.target.checked)}
              ></input>
              <label htmlFor="agreement" className="text-xs cursor-pointer">
                I agree to get emails about cool stuff on Reddit
              </label>
              <small className="block text-sm text-red-500">
                {errors.agreement}
              </small>
            </div>
            <InputGroup
              type="email"
              placeholder="Email"
              value={email}
              setValue={setEmail}
              error={errors.email}
            />
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
            <button className="w-full p-3 mb-4 text-xs font-bold text-white uppercase bg-blue-500 rounded hover:bg-blue-400">
              Sign up
            </button>
            <small>
              Already a redditor?
              <Link
                to="/login"
                className="ml-1 font-bold text-blue-500 uppercase"
              >
                Log In
              </Link>
            </small>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
