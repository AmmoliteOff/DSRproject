import { useEffect } from "react";
import { useState } from "react";
import axios from "axios";
import { Box, Card, CardContent, Typography} from "@mui/material";
import React from "react";
import AccountCard from "./AccountCard";
import Header from "./Header";

export default function Dashboard(props){
    const[userInfo, setUserInfo] = useState()
    const[isLoaded, setLoaded] = useState(false)
    //const[debts, setDebts] = useState()
    
    const axiosInstance = axios.create({
        withCredentials: true
    })

    function loadUserInfo(){
        axiosInstance.get("http://localhost:8080/api/getUserInfo")
        .then(res=>{
            if(res.status === 200){
            
                setUserInfo(res.data)
                setLoaded(true)
                // axiosInstance.get("http://localhost:8080/api/getDebts").then(
                //     resD=>{
                //         setDebts(resD.data)
                //         setLoaded(true)
                //     }
                // )
            }
            else if(res.status === 401){
                props.updateAuthStatus(false)
            }
        }
        )
        .catch(res=>{
            // if(res.response.status === 401){
            //     props.updateAuthStatus(false)
            // }
            console.log(res)
        }   
        )
    }

    useEffect(()=>{
        if(!isLoaded){
                loadUserInfo()
        }
    },[isLoaded])
    return(
        <React.Fragment>
            {isLoaded?
        <Box className = "dashboardContainer" sx={{width:"100%", height:"100vh"}}>
            <Header imgLink= {userInfo.imgLink} userInfo={userInfo} updateAuthStatus={props.updateAuthStatus}/>
                <Card sx={{borderRadius: "20px", margin:'2%', boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
                    <CardContent sx={{display:'flex', alignItems:'center'}}>
                    {userInfo.totalDebt === 0?
                    <Typography sx={{fontSize:'2.5vw'}}>Задолжностей нет!</Typography>:
        
                    <Typography sx={{fontSize:'2.5vw'}}>Задолжность составляет <b>{userInfo.totalDebt}</b> ₽</Typography>
                    }  
                    </CardContent>
                </Card>

            <React.Fragment>
                {userInfo.accounts.map(obj=>{
                    return (<AccountCard key={obj.accountId} bill={obj}/>)
                })}
                </React.Fragment>
        </Box>:
        null}
        </React.Fragment>
    )
}