import { Box } from '@mui/system';
import * as React from 'react';
import DataTable from './OrderTable';
import WebBrowsers from './WebBrowsers';
import Loading from "../components/Loading";
import { useAuth0, withAuthenticationRequired } from "@auth0/auth0-react";

function Home() {
  return (
    <div>
      <Box paddingTop={8}>
        <h1 color={"white"} paddingbottom={'10'} paddingleft={'1'}>ORDER DETAILS</h1>
      </Box>
      
      <DataTable />
      {/* <WebBrowsers /> */}
    </div>
  );
}

export default withAuthenticationRequired(Home, {
  onRedirecting: () => <Loading />,
});
