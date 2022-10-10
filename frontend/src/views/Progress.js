import React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress';

function Progress(props) {
    return (
    <Grid container spacing={2}>
        <Grid item xs={2}>
            <Box paddingLeft={10} paddingRight={9}>
                <p>{props.data}</p>
            </Box>
        </Grid>

        <Grid item xs={10}>
            <Box paddingLeft={20} paddingRight={50} paddingTop={1}>
                <LinearProgress variant="determinate" value={props.progress}/>
            </Box>
        </Grid>
    </Grid>
    );
}

export default Progress;