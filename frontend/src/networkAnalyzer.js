import React from "react";
import { Router, Route, Switch } from "react-router-dom";
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

const NetworkAnalyzer = () => {
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

  function setApi() {
    const updatedJSON = {
      apiKey: ""
    }
    // fs.writeFile('./model/data.json', JSON.stringify(updatedJSON), (err) => {
    //   if (err) console.log('Error writing file:', err);
    // })
    isApiKey = true;
  }

  const handleSubmit = (event) => {
    // event.preventDefault()
    console.log(event.target.apiKey.value)
    localStorage.setItem('apiKey', event.target.apiKey.value);
  }
  return (
    <Router history={history}>
        <Box bgcolor={'#423F23'} color={'white'} w='100%' p={4} textAlign={"center"} fontFamily="Roboto" letterSpacing="30px" fontSize={42}>
          NETWORK TRAFFIC ANALYZER
        </Box>
        <NavBar />


        <Grid container spacing={2} backgroundColor='#595959' padding={5}>
          <Grid item xs={10}>
            <Box fontSize={20} color='white'>
              <b> Website: </b> Securities and Exchange Commission
            </Box>
          </Grid>

          <Grid item xs={2}>
            <Box fontSize={20} color='white'>
              <b> URL: </b> <Link href="https://www.sec.gov">https://www.sec.gov</Link>
            </Box>
          </Grid>
        </Grid>

        {isApiKey == false && isAuthenticated == true && (
              <form onSubmit={handleSubmit}>
                <TextField id="outlined-basic" name="apiKey" type="text" label="MACROMETA API KEY" variant="outlined" 
                          sx={{
                            '& > :not(style)': { m: 2, width: '201ch' },
                }}/>
                <Box
                    sx={{
                      display: 'flex',
                      flexDirection: { xs: 'column', md: 'row' },
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    <Button type="submit" style={{justifyContent: 'center'}}>Submit</Button>
                </Box>
                
              </form>
              
        // <Box
        //   component="form"
        //   sx={{
        //     '& > :not(style)': { m: 2, width: '201ch' },
        //   }}
        //   noValidate
        //   autoComplete="off"
        // >
        //   <input id="outlined-basic" name="apiKey" label="MACROMETA API KEY" variant="outlined" />
        //   <
        // </Box>
        )}

        {isApiKey == true && (
        <Switch>
          <Route path="/app" exact component={Home} />
          <Route path="/profile" component={Profile} />
          <Route path="/external-api" component={ExternalApi} />
          <Route path="/editor" component={Editor} />
        </Switch>)
        } 
        
    </Router>
  );
};

export default NetworkAnalyzer;
