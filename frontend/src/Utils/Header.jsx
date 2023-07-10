import { Avatar, Box, Typography } from "@mui/material"
import IconButton from '@mui/material/IconButton';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from "react-router-dom"
import axios from "axios";
import { useEffect, useState } from "react";
import React from "react";
import { BACKEND_API_URL } from "./constants";

export default function Header(props) {

    const [isLoaded, setLoaded] = useState(false)
    const [userInfo, setUserInfo] = useState([])
    const navigate = useNavigate()

    const axiosInstance = axios.create({
        withCredentials: true
    })

    function produceHeaderLoad() {
        axiosInstance.get(BACKEND_API_URL + "/api/getUserInfo").then(res => {
            if (res.status === 200) {
                if (res.data.name === null || res.data.surname === null) {
                    navigate("/settings")
                }
                setUserInfo(res.data)
                setLoaded(true)
                if (!!props.updateUserId)
                    props.updateUserId(res.data.userId)
            }
        })
    }

    function produceLogout() {
        axiosInstance.post("http://localhost:8080/logout").then(res => {
            if (res.status === 200) {
                props.updateAuthStatus(false)
            }
        })
    }

    useEffect(() => {
        if (!isLoaded) {
            produceHeaderLoad()
        }
    }, [])

    return (
        <React.Fragment>
            {isLoaded ?
                <Box height="10%" sx={{ width: '100%', display: 'flex', justifyContent: 'space-between', alignItems: 'center', boxShadow: '0 2px 5px rgba(0,0,0,0.5)', pl: '1%', pr: '1%' }}>
                    <IconButton onClick={produceLogout} aria-label="Выйти" size="large">
                        <LogoutIcon variant="contained" sx={{ margin: '2%', height: '3vw', width: '3vw' }} />
                    </IconButton>
                    <Box sx={{ display: 'flex', alignItems: 'center', margin: '4%' }}>
                        <Typography sx={{ fontSize: '2vw', mr: '2%' }}>{userInfo.name}</Typography>
                        <Typography sx={{ fontSize: '2vw', ml: '2%', mr: '4%' }}>{userInfo.surname}</Typography>
                        <Box>
                            <Avatar sx={{ width: 60, height: 60 }} alt="Аватар пользователя" src={userInfo.imgLink} />
                        </Box>
                    </Box>

                </Box> : null}
        </React.Fragment>
    )
}