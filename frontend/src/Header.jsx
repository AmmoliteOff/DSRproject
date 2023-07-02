import { Avatar, Box, Typography } from "@mui/material"
import IconButton from '@mui/material/IconButton';
import LogoutIcon from '@mui/icons-material/Logout';
import axios from "axios";
import { useEffect, useState } from "react";
import React from "react";

export default function Header(props){

    const[isLoaded, setLoaded] = useState(false)
    const[userInfo, setUserInfo] = useState([])

    const axiosInstance = axios.create({
        withCredentials: true
    })

    function produceHeaderLoad(){
        axiosInstance.get("http://localhost:8080/api/getUserInfo").then(res=>{
            if(res.status === 200){
                setUserInfo(res.data)
                setLoaded(true)
            }
        })
    }

    function produceLogout(){
        axiosInstance.post("http://localhost:8080/logout").then(res=>{
            if(res.status === 200){
                console.log(props.updateAuthStatus)
            props.updateAuthStatus(false)
        }})
    }

    useEffect(()=>{
        if(!isLoaded){
            produceHeaderLoad()
        }
    },[])

    return(
        <React.Fragment>
            {isLoaded?
        <Box height="10%" sx={{ width:'100%', display:'flex', justifyContent:'space-between', alignItems:'center', boxShadow:'0 2px 5px rgba(0,0,0,0.5)', pl:'1%', pr:'1%'}}>
                <IconButton onClick={produceLogout} aria-label="Выйти" size="large">
                    <LogoutIcon variant="contained" sx={{ margin: '2%', height: '3vw', width: '3vw'}}/>
                </IconButton>
            <Box sx={{display:'flex', alignItems:'center', margin:'4%'}}>
                <Typography sx={{fontSize:'2vw', mr:'2%'}}>{userInfo.name}</Typography>
                <Typography sx={{fontSize:'2vw', ml:'2%', mr:'4%'}}>{userInfo.surname}</Typography>
                <Box>
                    <Avatar sx={{width: 60, height: 60}} alt="Аватар пользователя" src={userInfo.imgLink} />
                </Box>
            </Box>

        </Box>:null}
        </React.Fragment>
    )
}