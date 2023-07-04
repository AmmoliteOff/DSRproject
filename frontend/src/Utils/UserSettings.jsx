import { Box, Button, Input } from "@mui/material";
import styled from "@emotion/styled";
import { useEffect, useState } from "react";
import axios from "axios";
import { BACKEND_API_URL } from "./constants";
import {useNavigate} from "react-router-dom"

export default function UserSettings(){

    const[isError, setError] = useState(true);

    const navigate = useNavigate()

    const axiosInstance = axios.create({
        withCredentials: true,
        headers:{'Content-Type': 'application/json'}
    })

    const SettingsBox = styled(Box)({
        boxShadow:'0 2px 5px rgba(0,0,0,0.5)',
        alignSelf: "center",
        borderRadius: "20px"
    });

    function sendUserData(){
        let name = document.getElementById("nameInput").value
        let surname = document.getElementById("surnameInput").value
        let imgLink = document.getElementById("imgLinkInput").value
        if(name !== "" && surname !== ""){
            axiosInstance.post(BACKEND_API_URL+"/api/settings", {
                name: name,
                surname: surname, 
                imgLink: imgLink
            }).then(res=>{
                if(res.status === 200){
                    navigate("/dashboard")
                }
            })
        }
        else{
            setError(true)
        }
    }

    useEffect(()=>{

    }, [isError])

    return(
        <Box sx={{width:"100vw", height:"100vh", display:"flex", justifyContent: "center"}}>
        <SettingsBox>
            <Box width={"40vw"} height={"30vh"} display={"flex"} flexDirection={"column"}>
                <Input error={!isError} required placeholder="Ваше имя" id="nameInput"/>
                <Input error={!isError} required placeholder="Ваша фамилия" id="surnameInput"/>
                <Input placeholder="Ссылка на ваше фото" id="imgLinkInput"/>
            <Button onClick={sendUserData}>Отправить</Button>
            </Box>
        </SettingsBox>
    </Box>
    )
}