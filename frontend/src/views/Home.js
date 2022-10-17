

// function Home() {
//   // const { isLoading } = useAuth0();

//   // if (isLoading) {
//   //   return <Loading />;
//   // }

//   return (
//     <ChakraProvider theme={theme}>
//       <Box bg='#423F23' w='100%' p={4} color='white' textAlign={"center"} fontFamily="Roboto" fontSize="6xl" letterSpacing="30px">
//         NETWORK TRAFFIC ANALYZER
//       </Box>

//       <Flex color='white'>
//             <Box bg='#47465B' flex='5' padding={'25'}>
//              <b> Website: </b> Securities and Exchange Commission
//             </Box>
//             <Box bg='#47465B' flex='1' padding={'25'}>
//             <b> URL: </b> <Link>https://www.sec.gov</Link>
//             </Box>
//         </Flex>
      

//       <DataTable></DataTable>

//       <WebBrowsers></WebBrowsers>
//     </ChakraProvider>
//   );
// }




import { Box } from '@mui/system';
import * as React from 'react';
import DataTable from './DataTable';
import WebBrowsers from './WebBrowsers';
import Loading from "../components/Loading";
import { useAuth0, withAuthenticationRequired } from "@auth0/auth0-react";

function Home() {
  return (
    <div>
      <Box paddingTop={8}>
        <h1 color={"white"} paddingbottom={'10'} paddingleft={'1'}>TOP 5 NETWORK TRAFFIC</h1>
      </Box>
      
      <DataTable />
      {/* <WebBrowsers /> */}
    </div>
  );
}

export default withAuthenticationRequired(Home, {
  onRedirecting: () => <Loading />,
});
