import React from 'react';
import {
  ChakraProvider,
  Box,
  Text,
  Link,
  VStack,
  Code,
  Grid,
  theme,
} from '@chakra-ui/react';
import { ColorModeSwitcher } from './ColorModeSwitcher';
import { Logo } from './Logo';
import DataTable from './DataTable';
import WebBrowsers from './WebBrowsers';
import { Flex } from '@chakra-ui/react';

function App() {
  return (
    <ChakraProvider theme={theme}>
      <Box bg='#423F23' w='100%' p={4} color='white' textAlign={"center"} fontFamily="Roboto" fontSize="6xl" letterSpacing="30px">
        NETWORK TRAFFIC ANALYZER
      </Box>

      <Flex color='white'>
            <Box bg='#47465B' flex='5' padding={'25'}>
             <b> Website: </b> Securities and Exchange Commission
            </Box>
            <Box bg='#47465B' flex='1' padding={'25'}>
            <b> URL: </b> <Link>https://www.sec.gov</Link>
            </Box>
        </Flex>
      

      <DataTable></DataTable>

      <WebBrowsers></WebBrowsers>
    </ChakraProvider>
  );
}

export default App;
