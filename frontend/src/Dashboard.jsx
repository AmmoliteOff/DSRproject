import { useEffect } from "react";
import { useState } from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import { Box, Button, Typography, Dialog, DialogTitle, Input, Avatar, DialogContent} from "@mui/material";
import React from "react";
import AccountCard from "./Account/AccountCard";
import Header from "./Utils/Header";
import { BACKEND_API_URL } from "./Utils/constants";
import ErrorPage from "./ErrorPage";

export default function Dashboard(props){

    const axiosInstance = axios.create({
        withCredentials: true
      })
      const navigate = useNavigate();
    //const[debts, setDebts] = useState()
    const[isLoaded, setLoaded] = useState(false)
    const[accountsInfo, setAccountsInfo] = useState([])
    const[popUpState, setPopUpState] = useState(false)
    const[usersToAdd, setUsersToAdd] = useState([])
    const[lastUserInfo, setLastUserInfo] = useState(null)
    const[title, setTitle] = useState("")
    const[description, setDescription] = useState("")
    const[nextUserContainerEnabled, setNextUserContainerEnabled] = useState(false)
    const[isErrorDashboardLoad, setErrorDashboardLoad] = useState(false)

    function loadUserInfo(){
        axiosInstance.get(BACKEND_API_URL+"/api/accounts")
        .then(res=>{
            if(res.status === 200){
            
                setAccountsInfo(res.data)
                setLoaded(true)
            }
            else if(res.status === 401){
                props.updateAuthStatus(false)
            }
        }
        )
        .catch(res=>{
            if(res.message!=="Network Error"){
             if(res.response.status === 401){
                 props.updateAuthStatus(false)
             }
        } 
        else{
            setErrorDashboardLoad(true)
        } 
     }

        )
    }

    function changePopUpState(){
        if(popUpState){
            setNextUserContainerEnabled(false)
        }
        setPopUpState(!popUpState)
    }

    function fetchUser(event){
        if(usersToAdd.filter(user => user.userId == event.target.value).length === 0){
            if(event.target.value!=""){
            axiosInstance.get(BACKEND_API_URL+"/api/fetchUser", {params:{
                userId: event.target.value
            }}).then(res=>{
                setLastUserInfo(res.data)
                setNextUserContainerEnabled(true)
            }).catch(()=>setNextUserContainerEnabled(false))
        }
        else{
            setNextUserContainerEnabled(false)
        }
    }
    else{
        setNextUserContainerEnabled(false)
    }
    }

    function createAccount(){
        var userInAccount = ""
        for(let i = 0; i<usersToAdd.length; i++){
            if(i!=usersToAdd.length-1)
                userInAccount+=usersToAdd[i].userId+","
            else
                userInAccount+=usersToAdd[i].userId
        }

        axiosInstance.post(BACKEND_API_URL+"/api/createAccount", null, { params:{
            title: title,
            description: description,
            users: userInAccount
        }}).then((res)=>{
            if(res.status === 200){
                navigate(0)
            }
        })
    }

    function addUser(){
        var c = usersToAdd
        c.push(lastUserInfo)
        setUsersToAdd(c)
        setNextUserContainerEnabled(false)
    }

    function updateDescription(event){
        setDescription(event.target.value)
    }

    function updateTitle(event){
        setTitle(event.target.value)
    }

    useEffect(()=>{
        if(!isLoaded){
                loadUserInfo()
        }
    },[isLoaded, nextUserContainerEnabled])
    return(
        <React.Fragment>
             <Dialog onClose={changePopUpState} open={popUpState}>          
                <DialogTitle textAlign={'center'}>Создание счёта</DialogTitle>
                <DialogContent>
                <Box width={'50vh'}>
                    <Box display={"flex"} flexDirection={"column"} minWidth={0} gap={0.5}>  
                        <Input id="titleInput" onChange={updateTitle} placeholder="Введите название счёта"/>
                        <Input id="descriptionInput" onChange={updateDescription} placeholder="Введите описание счёта"/>
                        <Input id="idInput" onChange={fetchUser} placeholder="Введите ID пользователя"/>
                    </Box>
                    {nextUserContainerEnabled?
                    <Box onClick={addUser} display={"flex"} boxShadow={'0 2px 5px rgba(0,0,0,0.5)'} border={'1px solid'} borderColor={'rgb(170,170,170)'} padding={'1%'} marginTop={'2%'} marginBottom={'2%'} borderRadius={'20px'} gap={1} minWidth={0}> 
                        <Avatar src={lastUserInfo.imgLink}/>
                        <Typography>{lastUserInfo.name} {lastUserInfo.surname}</Typography>
                    </Box>:
                        null}
                        {usersToAdd.length!=0?
                                                <React.Fragment>
                        <Typography><b>Пользователи в счёте</b></Typography>
                            {usersToAdd.map(user=>{
                                return(
                                    <Box key={user.userId} display={"flex"} boxShadow={'0 2px 5px rgba(0,0,0,0.5)'} border={'1px solid'} borderColor={'rgb(170,170,170)'} padding={'1%'} marginTop={'2%'} marginBottom={'2%'} borderRadius={'20px'} gap={1} minWidth={0}>
                                    <Avatar src={user.imgLink}/>
                                    <Typography>{user.name} {user.surname}</Typography>
                                </Box>
                                )
                            })}
                        </React.Fragment>
                        :null}
                        <Box display={'flex'} justifyContent={'end'} marginTop={'2%'}>
                            <Button variant="contained" color="success" onClick={createAccount}>Создать счёт</Button>
                        </Box>
                </Box>
                </DialogContent>
            </Dialog>

             {isLoaded?
         <Box className = "dashboardContainer" sx={{height:"100vh"}}>
             <Header updateAuthStatus={props.updateAuthStatus}/>
             <Box display={"flex"} justifyContent={"end"} marginTop={"2%"} marginRight={"2%"}>
                <Button variant="contained" color="success" onClick={changePopUpState}>Создать счёт</Button>
             </Box>
             <React.Fragment>
                 {accountsInfo.map(obj=>{
                
                     return (
                            <AccountCard key={obj.accountId} account={obj}/>
                        )
                    
                 })}
                 </React.Fragment>
         </Box>:
         isErrorDashboardLoad?<ErrorPage/>:null}
        </React.Fragment>
    )
}