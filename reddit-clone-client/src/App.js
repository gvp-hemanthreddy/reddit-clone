import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";
import Home from "./components/Home";
import Subreddit from "./components/Subreddit";
import { AuthProvider } from "./context/Auth.js";

function App() {
  //TODO: Use <NavBar /> here so that we can remove from home and subreddit
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/" exact component={Home}></Route>
          <Route path="/register" component={Register}></Route>
          <Route path="/login" component={Login}></Route>
          <Route path="/r/:name" component={Subreddit}></Route>
        </Switch>
      </Router>
    </AuthProvider>
  );
}

export default App;
