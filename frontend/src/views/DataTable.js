import React, { useEffect, useState } from "react";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import DataService from "../service/DataService";
import { fetchEventSource } from "@microsoft/fetch-event-source";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Legend,
} from "recharts";
import Tooltip from '@mui/material/Tooltip';
const serverBaseURL = "http://localhost:8081";

function createData(source, date, time, traffic) {
  return { source, date, time, traffic };
}

function DataTable() {

  // const [data, setData] = useState([])

  // useEffect(() => {
  //   DataService.postData()
  //   const sse = new EventSource('http://localhost:8081/sse');
  //   let ls = []


  //   sse.onmessage = e => getRealtimeData(JSON.parse(e.data));

  //   return () => {
  //     sse.close();
  //   };
  // }, []);
  const [data, setData] = useState([]);

  useEffect(() => {

    let isMounted = true;

    function getRealtimeData(data) {
      let ls = []
      data.forEach(element => {
        ls.push(JSON.parse(element))
      });
      return ls;
    }

    const fetchData = async () => {
      await DataService.postData();
      await fetchEventSource(`${serverBaseURL}/traffic`, {
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
          // console.log(event.data);
          const parsedData = JSON.parse(event.data);
          const finalData = getRealtimeData(parsedData);
          console.log(finalData);

          if (isMounted) {
            setData(finalData);
          }
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

    return () => {
      // 👇️ when component unmounts, set isMounted to false
      isMounted = false;
    };
  }, []);
  

  const rows = data;
  // console.log(data)
  // const rows = [
  //   createData(data[0].ip, data[0].date, data[0].time, data[0].traffic),
  //   createData(data[1].ip, data[1].date, data[1].time, data[1].traffic),
  //   createData(data[2].ip, data[2].date, data[2].time, data[2].traffic),
  //   createData(data[3].ip, data[3].date, data[3].time, data[3].traffic),
  //   createData(data[4].ip, data[4].date, data[4].time, data[4].traffic),
  // ];
  return (
    <TableContainer sx={{ color: '#595959' }} component={Paper} backgroundcolor='#595959' padding={'10'} >
      <Table sx={{ minWidth: 650 }} aria-label="simple table" variant='simple' >
        <TableHead> 
          <TableRow>
            <TableCell>Source</TableCell>
            <TableCell align="right">Date</TableCell>
            <TableCell align="right">Time</TableCell>           
            <TableCell align="right"><Tooltip title="Amount of downloaded data traffic"><div>Traffic</div></Tooltip></TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {rows.slice(0).reverse().map((row, i) => (
            <TableRow
              key={i}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">{row.ip}</TableCell>
              <TableCell align="right" >{row.date}</TableCell>
              <TableCell align="right">{row.time}</TableCell>
              <TableCell align="right">{Math.round(row.traffic/1000)/1000}GB</TableCell>
            </TableRow>
          ))}
        </TableBody>

      </Table>
    </TableContainer>
  );
}

export default DataTable;