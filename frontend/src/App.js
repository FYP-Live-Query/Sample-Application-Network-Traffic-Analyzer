import React from "react";
import { Router, Route, Switch, Redirect, BrowserRouter} from "react-router-dom";
import { Button, Container } from "reactstrap";

import Loading from "./components/Loading";
import NavBar from "./components/NavBar";
import Footer from "./components/Footer";
import Home from "./views/Home";
import Profile from "./views/Profile";
import ExternalApi from "./views/ExternalApi";
import { useAuth0 } from "@auth0/auth0-react";
import history from "./utils/history";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import { useState, useEffect } from "react";
import Editor from "./views/Editor";
import NetworkAnalyzer from "./networkAnalyzer";
import Main from "./views/Main";

// styles
import "./App.css";

// fontawesome
import initFontAwesome from "./utils/initFontAwesome";
import { display, flexbox, width } from "@mui/system";
initFontAwesome();
const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));
const App = () => {
  // const [apiKey, setApiKey] = useState([]);

  // useEffect(() => {
  //   localStorage.setItem('apiKey', JSON.stringify(apiKey));
  // }, [apiKey]);
  // localStorage.clear();
  const { isLoading, error, isAuthenticated } = useAuth0();

  // const apiKey = myData[0].apiKey;
  // let isApiKey = apiKey !== "";
  const apiKey = localStorage.getItem('apiKey');
  console.log(apiKey);
  let isApiKey = apiKey !== null;
  console.log(isApiKey);
  if (error) {
    return <div>Oops... {error.message}</div>;
  }

  if (isLoading) {
    return <Loading />;
  }

  return (

    <Router history={history}>
      <Switch>
        <Route path="/" exact component={Main} />
        <Route path="/app" exact component={NetworkAnalyzer} />
        <Route path="/editor" exact component={Editor} />
      </Switch>
    </Router>
  );
};

export default App;
