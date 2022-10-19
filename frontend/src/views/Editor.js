
import { Box } from '@mui/system';
import React, { useEffect, useState } from "react";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import MuiGrid from '@mui/material/Grid';
import Divider from '@mui/material/Divider';
import DataTable from './DataTable';
import WebBrowsers from './WebBrowsers';
import Loading from "../components/Loading";
import { useAuth0, withAuthenticationRequired } from "@auth0/auth0-react";
import { fetchEventSource } from "@microsoft/fetch-event-source";
import DataService from '../service/DataService';

const serverBaseURL = "http://localhost:8081";

function Data(query) {
    // const [data, setData] = useState([]);
    const fetchData = async () => {
      await DataService.postQuery(query);
      await fetchEventSource(`${serverBaseURL}/query`, {
        method: "GET",
        headers: {
          Accept: "text/event-stream",
        },
        onopen(res) {
          if (res.ok && res.status === 200) {
            console.log("Connection made ", res);
          } else if (
            res.status >= 400 &&
            res.status < 500 &&
            res.status !== 429
          ) {
            console.log("Client side error ", res);
          }
        },
        onmessage(event) {
          console.log(event.data);
          const div = document.getElementById('paragraph');
          div.textContent = event.data;
          // const parsedData = JSON.parse(event.data);
          // const finalData = getRealtimeData(parsedData);
          // console.log(finalData);
  
          // if (isMounted) {
            // setData(event.data);
          // }
        },
        onclose() {
          console.log("Connection closed by the server");
        },
        onerror(err) {
          console.log("There was an error from server", err);
        },
      });
    };
    fetchData();
}
const handleSubmit = (event) => {
  event.preventDefault()
  // fetchData();
  console.log(event.target.apiKey.value);
  Data(event.target.apiKey.value);
}

function Editor() {
  return (
    <div>
    <form onSubmit={handleSubmit}>
      <TextField id="outlined-basic" name="apiKey" type="text" label="QUERY" variant="outlined" 
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
    <p id='paragraph'>

    </p>
    </div>
    // <div>
        
    //     <Divider orientation="vertical" flexItem>
    //     <TextField sx={{m:10}} id="outlined-basic" label="Query" varint="outlined" />
    //     <Button variant="contained">Contained</Button>
    //     </Divider>
    // </div>
  );
}

export default Editor;
