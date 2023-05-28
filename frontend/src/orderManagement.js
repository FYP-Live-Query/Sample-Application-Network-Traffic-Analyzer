import React from "react";
import { Router, Route, Switch } from "react-router-dom";
import { Button, Container } from "reactstrap";

import Loading from "./components/Loading";
import NavBar from "./components/NavBar";
import Footer from "./components/Footer";
import OrderHome from "./views/OrderHome";
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

const OrderManagement = () => {

  const { isLoading, error, isAuthenticated } = useAuth0();

  if (error) {
    return <div>Oops... {error.message}</div>;
  }

  if (isLoading) {
    return <Loading />;
  }

  function setApi() {
    const updatedJSON = {
    }
  }

  const handleSubmit = (event) => {

   
  }
  return (
    <Router history={history}>
        <Box bgcolor={'#423F23'} color={'white'} w='100%' p={4} textAlign={"center"} fontFamily="Roboto" letterSpacing="30px" fontSize={42}>
          ORDER MANAGEMENT
        </Box>
        <NavBar />


        <Grid container spacing={2} backgroundColor='#595959' padding={5}>
          <Grid item xs={10}>
            <Box fontSize={20} color='white'>
              <b> Website: </b> Excel BI Analytics
            </Box>
          </Grid>

          <Grid item xs={2}>
            <Box fontSize={20} color='white'>
              <b> URL: </b> <Link href="https://excelbianalytics.com/wp/">excelbianalytics.com</Link>
            </Box>
          </Grid>
        </Grid>


        <Switch>
          <Route path="/order" exact component={OrderHome} />
          <Route path="/profile" component={Profile} />
          <Route path="/external-api" component={ExternalApi} />
          <Route path="/editor" component={Editor} />
        </Switch>
    </Router>
  );
};

export default OrderManagement;
