import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";
import Home from "./components/Home";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={Home}></Route>
        <Route path="/register" component={Register}></Route>
        <Route path="/login" component={Login}></Route>
      </Switch>
    </Router>
  );
}

export default App;
