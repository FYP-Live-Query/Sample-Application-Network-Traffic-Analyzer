
import { Box } from '@mui/system';
import React, { useEffect, useState } from "react";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import { fetchEventSource } from "@microsoft/fetch-event-source";
import DataService from '../service/DataService';



const serverBaseURL = "http://localhost:8081";

function QueryEditor() {
  const [data, setData] = useState([]);
  let prevData = {ip: '', date: '', browser: '', traffic: 0};

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
        // data.push(parsedData)
        // arr.push(parsedData);
        setData(parsedData);
        // console.log(parsedData);

        // const time = document.getElementById('time');
        // time.innerHTML = "Time: " + "<br>" + parsedData[1];

        // const results = document.getElementById('results');
        // results.innerHTML = "Data: " + "<br>" + parsedData[0];

        const divElement = document.getElementById('paragraph');
        
        const newData = JSON.parse(parsedData[0]);
        // console.log("New: ", newData.time);
        prevData = JSON.stringify(prevData) === JSON.stringify({ip: '', date: '', browser: '', traffic: 0}) ? newData : prevData;
        prevData = JSON.stringify(newData) === JSON.stringify(prevData) ? newData : prevData;



        divElement.innerHTML += `<div id="whole" style="display: flex">` +
        `<div id="time" style="flex: 1; margin-left: 52px; color: ${newData == prevData ? 'tomato' : 'blue'};"> Time: <br> ${parsedData[1].substring(0,8)} </div><br><br>` +
        `<div id="ip" style="flex: 2; color: ${newData.ip == prevData.ip ? 'tomato' : 'blue'};">` + "IP Address: " + "<br>" + newData.ip + `</div>` +
        `<div id="results" style="flex: 2; color: ${newData.date == prevData.date ? 'tomato' : 'blue'};">` + "Date: " + "<br>" + newData.date + '</div>' +
        `<div id="browser" style="flex: 2; color: ${newData.browser == prevData.browser ? 'tomato' : 'blue'};">` + "Browsers: " + "<br>" + newData.browser + `</div>` +
        `<div id="traffic" style="flex: 2; color: ${newData.traffic == prevData.traffic ? 'tomato' : 'blue'};"> Traffic: <br> ${newData.traffic} </div>` +
        `</div>`;

        divElement.innerHTML += "<hr><br><br>" ;
        
        prevData = newData;
        // let traffic = document.getElementById('traffic');
        // if (newData.traffic == prevData.traffic) {          
        //   traffic.style.color = 'blue';
        // } 

        // const time = document.getElementById('time');

        // time.style.flex = '1';

        // const results = document.getElementById('results');
        // results.style.flex = '2';


        // const info = document.getElementById('data');
        // info.innerHTML += parsedData.slice(0,5) + "<br>";        
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
            {/* <div id="time"></div>
            <div id="results"></div> */}
          {/* {data.forEach((el)=>{
              <div>el</div>
          })} */}
          {/* Timestamp: {data[5]}
          Data: {data.slice(0,5)} */}
          
          {/* <TableContainer sx={{ color: '#595959' }}  backgroundcolor='#595959' padding={'10'} >
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
                    id = "row"
                  >
                  {data.map((object, i) => 
                    <div>
                      <TableCell id="time" component="th" scope="row">object[5]</TableCell> 
                      <TableCell id="data" align="right" >object.slice(0, 5)</TableCell>

                      <TableCell id="time" component="th" scope="row">object[5]</TableCell> 
                      <TableCell id="data" align="right" >object.slice(0, 5)</TableCell>
                    </div>
                  )}
                  </TableRow>

              </TableBody>

            </Table>
          </TableContainer> */}

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
