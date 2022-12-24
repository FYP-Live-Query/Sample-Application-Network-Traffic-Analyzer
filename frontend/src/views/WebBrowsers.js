import React, { useEffect, useState } from "react";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress';
import Progress from './Progress';
import BrowserService from "../service/BrowserService";
import { fetchEventSource } from "@microsoft/fetch-event-source";

const serverBaseURL = "http://localhost:8081";
// const newData = []
// Example component that utilizes the `normalise` function at the point of render.
function WebBrowsers() {
    const [data, setData] = useState([]);

    useEffect(() => {
  
      let isMounted = true;
      const browserNames = {
        'chr': 'Google Chrome',
        'fox': 'Mozilla Firefox',
        'saf': 'Safari',
        'opr': 'Opera',
        'mie': 'Internet Explorer'
      }
      function getRealtimeData(data) {
        let ls = []
        data.forEach(element => {
            const jsonData = JSON.parse(element)
            let data = {
                browser: browserNames[jsonData.browser],
                totalCount: jsonData.totalCount
            }
            ls.push(data)
        });
        return ls;
      }
  
      const fetchData = async () => {
        await BrowserService.postData();
        await fetchEventSource(`${serverBaseURL}/browsers`, {
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
            // const finalData = getRealtimeData(parsedData);
            // setData(finalData)
            console.log("Browser Data: ", parsedData);
  
            if (isMounted) {
              setData(parsedData);
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


    // let browsers = data;
    let newData = data;
    var total = 0;
    newData.forEach(getSum);
    function getSum(item) {
      if (Number.isInteger(item[1])){
        // newData.push(item)
        total += item[1];
      }
    }

    const browserData = newData.map((item,i)=>(
        <Progress key = {i} data={item[0]} progress={(item[1]/total)*100}/>
    ));

  return (
    <React.Fragment>
        <Box paddingTop={8}>
            <h1>TOP 05 WEB BROWSERS</h1>
        </Box>
        <Box paddingTop={2}>
            {browserData}
            {/* <Progress data={'Google Chrome'} progress={(browsers[0].totalCount/total)*100}/>
            <Progress data={'Microsoft Edge'} progress={(browsers[1].totalCount/total)*100}/>
            <Progress data={'Safari'} progress={(browsers[2].totalCount/total)*100}/>
            <Progress data={'Mozilla Firefox'} progress={(browsers[3].totalCount/total)*100}/>
            <Progress data={'Internet Explorer'} progress={(browsers[4].totalCount/total)*100}/> */}
        </Box>
    </React.Fragment>
  );
}

export default WebBrowsers;

// import React from 'react';
// import {
//   ChakraProvider,
//   Box,
//   Text,
//   Link,
//   VStack,
//   Code,
//   Grid,
//   theme,
//   Heading,
// } from '@chakra-ui/react';
// import { Center, Square, Circle } from '@chakra-ui/react'
// import { Flex, Spacer } from '@chakra-ui/react'
// import { Progress } from '@chakra-ui/react'

// function WebBrowsers() {
// return (
//     <div>
//         <Heading bg={'#47465B'} textColor={"white"} padding={'50'}>TOP 5 WEB BROWSERS</Heading>

//         <Flex color='white'>
//             <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
//                 <Text>Google Chrome</Text>
//             </Center>
//             <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
//             <Progress value={80} marginTop={'1.5'}/>
//             </Box>
//         </Flex>

//         <Flex color='white'>
//             <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
//                 <Text>Microsoft Edge</Text>
//             </Center>
//             <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
//             <Progress value={80} marginTop={'1.5'}/>
//             </Box>
//         </Flex>

//         <Flex color='white'>
//             <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
//                 <Text>Safari</Text>
//             </Center>
//             <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
//             <Progress value={80} marginTop={'1.5'}/>
//             </Box>
//         </Flex>

//         <Flex color='white'>
//             <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
//                 <Text>Mozilla Firefox</Text>
//             </Center>
//             <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
//             <Progress value={80} marginTop={'1.5'}/>
//             </Box>
//         </Flex>

//         <Flex color='white'>
//             <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
//                 <Text>Microsoft Edge</Text>
//             </Center>
//             <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
//             <Progress value={80} marginTop={'1.5'}/>
//             </Box>
//         </Flex>        
//     </div>
// );
// }

// export default WebBrowsers;
