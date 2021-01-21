import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";
import Home from "./components/Home";
import Subreddit from "./components/Subreddit";
import { AuthProvider } from "./context/Auth.js";
import PostPage from "./components/PostPage";
import CreatePost from "./components/CreatePost";

function App() {
  //TODO: Use <NavBar /> here so that we can remove from home and subreddit
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/" exact component={Home}></Route>
          <Route path="/register" exact component={Register}></Route>
          <Route path="/login" exact component={Login}></Route>
          <Route path="/r/:name" exact component={Subreddit}></Route>
          <Route path="/r/:subredditname/submit" exact component={CreatePost}></Route>
          <Route path="/r/:subreddit/:postIdentifier/:slug" exact component={PostPage}></Route>
        </Switch>
      </Router>
    </AuthProvider>
  );
}

export default App;
