import React, { useEffect, useState } from "react";
import {
  ChakraProvider,
  Box,
  Text,
  Link,
  VStack,
  Code,
  Grid,
  theme,
  Heading,
  Button,
} from '@chakra-ui/react';
import { ColorModeSwitcher } from './ColorModeSwitcher';
import { Logo } from './Logo';
import {
    Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
  } from '@chakra-ui/react'
import DataService from "./DataService";

function DataTable() {
  const [data, setData] = useState([])

  useEffect(() => {
      getData()
  }, [])

  const getData = () => {
      DataService.getData().then((response) => {
          setData(response.data)
          console.log(response.data);
      });
  }
return (
<TableContainer bg={'#2D2C24'} padding={'10'}>
<Heading textColor={"white"} paddingBottom={'10'} paddingLeft={'1'}>TOP 5 NETWORK TRAFFIC</Heading>
  <Table variant='simple' colorScheme='facebook'> 
    <Thead textColor={"white"}>
      <Tr>
        <Th textColor={"white"}>Source</Th>
        <Th textColor={"white"}>Date</Th>
        <Th textColor={"white"}>Time</Th>
        <Th textColor={"white"}>Traffic</Th>
      </Tr>
    </Thead>
    <Tbody textColor={"white"}>
      <Tr>
        <Td>127.0.0.1</Td>
        <Td onMouseOverCapture={getData}>{data}</Td>
        <Td>25.4</Td>
        <Td>384KB</Td>
      </Tr>
      <Tr>
        <Td>127.0.0.1</Td>
        <Td>09-01-2022</Td>
        <Td>25.4</Td>
        <Td>384KB</Td>
      </Tr>
      <Tr>
        <Td>127.0.0.1</Td>
        <Td>09-01-2022</Td>
        <Td>25.4</Td>
        <Td>384KB</Td>
      </Tr>
      <Tr>
        <Td>127.0.0.1</Td>
        <Td>09-01-2022</Td>
        <Td>25.4</Td>
        <Td>384KB</Td>
      </Tr>
      <Tr>
        <Td>127.0.0.1</Td>
        <Td>09-01-2022</Td>
        <Td>25.4</Td>
        <Td>384KB</Td>
      </Tr>
    </Tbody>
  </Table>
</TableContainer>);
}

export default DataTable;
