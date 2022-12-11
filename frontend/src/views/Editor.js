
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
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
// import SimpleUserTable from './SimpleUserTable';

const serverBaseURL = "http://localhost:8081";

function QueryEditor() {
  const [data, setData] = useState([]);


      // const [data, setData] = useState([]);
  const fetchData = async (query) => {
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
        
        const parsedData = JSON.parse(event.data);
        // console.log(parsedData);
        data.push(parsedData)
        // arr.push(parsedData);
        setData(data);
        console.log(data);
        // const div = document.getElementById('paragraph');
        // div.textContent = event.data;
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
        console.log("There was an error from serve", err);
      },
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault()
    // fetchData();
    console.log(event.target.apiKey.value);
    fetchData(event.target.apiKey.value);
  }

    return (
      <div>
      <Box id='header' bgcolor={'#000000'} color={'white'} w='100%' p={4} textAlign={"center"} fontFamily="Roboto" letterSpacing="30px" fontSize={42}>
        QUERY EDITOR
      </Box>
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
      <div id='paragraph'>
          {/* {data.forEach((el)=>{
              <div>el</div>
          })} */}
          {/* Timestamp: {data[5]}
          Data: {data.slice(0,5)} */}
          <TableContainer sx={{ color: '#595959' }}  backgroundcolor='#595959' padding={'10'} >
            <Table sx={{ minWidth: 650 }} aria-label="simple table" variant='simple' >
              <TableHead> 
                <TableRow>
                  <TableCell>Time</TableCell>
                  <TableCell align="right">Data</TableCell>
                </TableRow>
              </TableHead>

              <TableBody>


                  
                  <TableRow
                    key={data[5]}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                  {data.map((object, i) => 
                    
                    <div>

                      <TableCell component="th" scope="row">{object[5]} </TableCell> 
                      <TableCell align="right" >{object.slice(0,5)}</TableCell>
                    </div>
                  )}
                  </TableRow>

              </TableBody>

            </Table>
          </TableContainer>
          {/* <SimpleUserTable data={data.users} isFetching={data.isFetching} /> */}
      </div>
      </div>
      // <div>
          
      //     <Divider orientation="vertical" flexItem>
      //     <TextField sx={{m:10}} id="outlined-basic" label="Query" varint="outlined" />
      //     <Button variant="contained">Contained</Button>
      //     </Divider>
      // </div>
    );

}


export default QueryEditor;
