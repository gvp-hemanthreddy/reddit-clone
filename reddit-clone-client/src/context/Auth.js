import { createContext, useReducer, useContext, useEffect } from "react";
import LocalStorageService from "../utils/LocalStorageService";

const StateContext = createContext({
  authenticated: false,
  username: null,
});

const DispatchContext = createContext();

const authReducer = (state, action) => {
  const { type, payload } = action;
  switch (type) {
    case "LOGIN":
      return { ...state, authenticated: true, username: payload };
    case "LOGOUT":
      return { ...state, authenticated: false, username: null };
    default:
      console.error(`Unknown reducer action type : ${type}`);
  }
};

const AuthProvider = ({ children }) => {
  const [state, defaultDispatch] = useReducer(authReducer, {
    authenticated: false,
    username: null,
  });

  const dispatch = (type, payload) => defaultDispatch({ type, payload });

  useEffect(() => {
    const username = LocalStorageService.getUsername();
    if (username) {
      dispatch("LOGIN", username);
    }
  }, []);

  return (
    <DispatchContext.Provider value={dispatch}>
      <StateContext.Provider value={state}>{children}</StateContext.Provider>
    </DispatchContext.Provider>
  );
};

export const useAuthState = () => useContext(StateContext);
export const useAuthDispatch = () => useContext(DispatchContext);

export { AuthProvider };
