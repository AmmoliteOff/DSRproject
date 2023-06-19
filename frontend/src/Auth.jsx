import axios from "axios"
import {useEffect, useState} from "react"
import styled from "@emotion/styled";
import { Box, Button, TextField, Typography} from "@mui/material";

export default function Auth(props){
    const[emailInputError, setEmailInputError] = useState(false)
    const[codeInputError, setCodeInputError] = useState(false)
    const[emailConfirmed, setEmailConfirm] = useState(false)
    const[email, setEmail] = useState("")

    const axiosInstance = axios.create({
        withCredentials: true
    })

    const AuthBox = styled(Box)({
        boxShadow:'0 2px 5px rgba(0,0,0,0.5)',
        alignSelf: "center",
        borderRadius: "20px"
    });


    function produceAuth(){
        var code = document.getElementById("codeInput").value
            axiosInstance.post("http://localhost:8080/login", null, {params:{ 
        email,
        code,
        "remember-me": true
        }}).then(res=>{
            if(res.status === 200){
                setCodeInputError(false);
                props.updateAuthStatus(true)
            }
            else{
                setCodeInputError(true);
            }
        }
        ).catch(e=>{
            setCodeInputError(true);
        })
    }

    function sendCode(){
    var localEmail = document.getElementById("emailInput").value
    if(localEmail.includes("@")){
        setEmailInputError(false);
        setEmail(localEmail)
        axiosInstance.post("http://localhost:8080/auth/sendCode", null, {params:{ 
                email: localEmail
        }}).then(res=>{
            if(res.status === 200){
                setEmailConfirm(true)
            }
            else{}
        }
        ).catch()
        }
            else{
                setEmailInputError(true);
            }
    }

    return(
        <Box sx={{width:"100vw", height:"100vh", display:"flex", justifyContent: "center"}}>
            <AuthBox>
                {!emailConfirmed?
                <Box className="emailBox">
                    <Typography fontSize={"3.0vw"} sx={{marginTop: "3%", textAlign:'center'}}>Введите Email</Typography>
                    <Typography fontSize={"1.7vw"} sx={{margin:"3%" ,textAlign:'center', color: "gray"}}>Мы отправим вам код, чтобы вы могли легко войти или зарегистрироваться</Typography>
                    <Box display={"flex"} flexDirection={"column"} width={"65%"} alignItems={"center"} justifyContent="center" margin="auto">
                        <TextField fullWidth error={emailInputError} helperText={emailInputError?"Проверьте правильность указанной почты":null} label="Email" variant="outlined"id="emailInput" sx={{marginBottom: "2%"}}/>
                        <Button margin="2%" type="submit" variant="contained" color="success" sx = {{marginBottom: "2%", borderRadius: "20px"}} onClick={sendCode}>Отправить код</Button>
                    </Box>
                </Box>
                :
                <Box className="codeBox">
                    <Typography fontSize={"3.0vw"} sx={{marginTop: "3%", textAlign:'center'}}>Введите код</Typography>
                    <Typography fontSize={"1.7vw"} sx={{margin:"3%" ,textAlign:'center', color: "gray"}}>Код отправлен на адрес {email}. Проверьте почту и введите код.</Typography>
                    <Box display={"flex"} flexDirection={"column"} width={"65%"} alignItems={"center"} justifyContent="center" margin="auto">
                        <TextField fullWidth error={codeInputError} helperText={codeInputError?"Код не подходит":null} label="Код" variant="outlined" id="codeInput" sx={{width: "100%" , marginBottom: "2%"}}/>
                    
                        <Button type="submit" variant="contained" color="success" sx = {{marginBottom: "2%",borderRadius: "20px"}} onClick={produceAuth}>Войти</Button>
                    </Box>
                </Box>}
            </AuthBox>
        </Box>
    )
    
}