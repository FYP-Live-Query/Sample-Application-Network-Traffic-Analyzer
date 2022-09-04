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
  Heading,
} from '@chakra-ui/react';
import { Center, Square, Circle } from '@chakra-ui/react'
import { Flex, Spacer } from '@chakra-ui/react'
import { Progress } from '@chakra-ui/react'

function WebBrowsers() {
return (
    <div>
        <Heading bg={'#47465B'} textColor={"white"} padding={'50'}>TOP 5 WEB BROWSERS</Heading>

        <Flex color='white'>
            <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
                <Text>Google Chrome</Text>
            </Center>
            <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
            <Progress value={80} marginTop={'1.5'}/>
            </Box>
        </Flex>

        <Flex color='white'>
            <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
                <Text>Microsoft Edge</Text>
            </Center>
            <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
            <Progress value={80} marginTop={'1.5'}/>
            </Box>
        </Flex>

        <Flex color='white'>
            <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
                <Text>Safari</Text>
            </Center>
            <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
            <Progress value={80} marginTop={'1.5'}/>
            </Box>
        </Flex>

        <Flex color='white'>
            <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
                <Text>Mozilla Firefox</Text>
            </Center>
            <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
            <Progress value={80} marginTop={'1.5'}/>
            </Box>
        </Flex>

        <Flex color='white'>
            <Center w='200px' bg={'#47465B'} paddingBottom={'5'}>
                <Text>Microsoft Edge</Text>
            </Center>
            <Box flex='1' bg={'#47465B'} paddingLeft={'10'} paddingRight={'100'}>
            <Progress value={80} marginTop={'1.5'}/>
            </Box>
        </Flex>        
    </div>
);
}

export default WebBrowsers;
