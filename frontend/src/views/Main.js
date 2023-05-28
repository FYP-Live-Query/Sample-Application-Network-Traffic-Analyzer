import Box from '@mui/material/Box';

import Link from '@mui/material/Link';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';

const style = {
  textAlign: 'center',
  bgcolor: 'background.paper',
  margin: "auto",
};


function Main() {
  return (
    <div id = "main">
    <Box id='header' bgcolor={'#000000'} color={'white'} w='100%' p={4} textAlign={"center"} fontFamily="Roboto" letterSpacing="30px" fontSize={42}>
      LIVE QUERY APPLICATIONS
    </Box>

    {/* <List sx={{ width: '100%', alignContent: 'center', bgcolor: 'background.paper' }}>
      <ListItem>
        <Link href="/app" underline="none">
            <Button margin={50} >
                Network Analyzer - Sample Application 01
            </Button>
        </Link>
      </ListItem>
      <ListItem>
        <Link href="/editor" underline="none">
            <Button>
                Query Editor
            </Button>
        </Link>
      </ListItem>
    </List> */}
    <List sx={style} component="nav" aria-label="mailbox folders">
      <ListItem button>
        
        <Link href="/app" underline="none">
          <ListItemText style={{ display: "flex", justifyContent: "flex-start", justify: "center" }} primary="Network Analyzer - Sample Application 01" />
        </Link>
      </ListItem>

      <Divider />

      <ListItem button>
        
        <Link href="/order" underline="none">
          <ListItemText style={{ display: "flex", justifyContent: "flex-start", justify: "center" }} primary="Order Management - Sample Application 02" />
        </Link>
      </ListItem>

      <Divider />
      <ListItem button divider>
        
        <Link href="/editor" underline="none">
        <ListItemText style={{ display: "flex", justifyContent: "flex-start", justify: "center" }} primary="Query Editor" />
        </Link>
      </ListItem>
    </List>

    </div>

  );
}

export default Main;
