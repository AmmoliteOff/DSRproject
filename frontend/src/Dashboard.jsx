import { useEffect } from "react";
import { useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import axios from "axios";
import { Box, Button, Card, CardContent, Typography} from "@mui/material";
import React from "react";
import Account from "./Account";
import AccountCard from "./AccountCard";
import Header from "./Header";

export default function Dashboard(props){

    const axiosInstance = axios.create({
        withCredentials: true
      })
    //const[debts, setDebts] = useState()
    const[isLoaded, setLoaded] = useState(false)
    const[userInfo, setUserInfo] = useState([])

    function loadUserInfo(){
        axiosInstance.get("http://localhost:8080/api/getUserInfo")
        .then(res=>{
            if(res.status === 200){
            
                setUserInfo(res.data)
                setLoaded(true)
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
            props.updateAuthStatus(false)
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
             <Header updateAuthStatus={props.updateAuthStatus}/>
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
                
                     return (
                        // <Link to ={"accouhdfghdfhnt"}>
                            <AccountCard key={obj.accountId} account={obj}/>
                        // </Link>
                        )
                    
                 })}
                 </React.Fragment>
         </Box>:
         null}
        </React.Fragment>
        // </React.Fragment>
    )
}