import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { useAuthDispatch, useAuthState } from "../context/Auth";
import axios from "axios";
import LocalStorageService from "../utils/LocalStorageService";

function NavBar() {
  const { authenticated } = useAuthState();
  const dispatch = useAuthDispatch();
  const refreshToken = LocalStorageService.getRefreshToken();

  const logout = () => {
    axios
      .post("auth/logout", {
        refreshToken,
      })
      .then(() => {
        dispatch("LOGOUT", {});
        LocalStorageService.clearTokens();
        window.location.reload();
      })
      .catch((err) => console.error("Error while logging out" + err));
  };

  return (
    <div className="inset-x-0 top-0 z-10 flex items-center justify-between h-12 px-5 bg-white">
      {/* Logo */}
      <div className="flex">
        <Link to="/">
          <img
            src="/images/redditLogo.svg"
            className="w-8 h-8 mr-2"
            alt="Reddit Logo"
          />
        </Link>
        <span className="hidden text-2xl font-semibold lg:block">
          <Link to="/">reddit</Link>
        </span>
      </div>

      {/* Auth buttons */}
      <div className="flex items-center">
        {authenticated ? (
          <button className="mr-4 button blue hollow" onClick={logout}>
            Logout
          </button>
        ) : (
          <Fragment>
            <Link to="/login" className="mr-4 button blue hollow">
              Log in
            </Link>
            <Link to="/register" className="button blue">
              Sign Up
            </Link>
          </Fragment>
        )}
      </div>
    </div>
  );
}

export default NavBar;
