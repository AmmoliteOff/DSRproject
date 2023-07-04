import { Typography, Box, Card, CardActionArea, CardContent, Dialog, DialogTitle, DialogContent, Input, Button } from "@mui/material";
import Header from "../Utils/Header";
import { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate} from "react-router-dom"
import { BACKEND_API_URL } from "../Utils/constants";
import BillCard from "./BillCard";
import BillInfoTable from "./BillInfoTable";
import React from "react"
import BackButton from "../Utils/BackButton";

export default function Bill(props){

    function isNumeric(str) {
        if (typeof str != "string") return false
        return !isNaN(str) &&
               !isNaN(parseFloat(str))
      }


    const navigate = useNavigate();
    const[isLoaded, setLoaded] = useState(false)
    const[billData, setBillData] = useState([])
    const {billId} = useParams()
    const {accountId} = useParams()
    const [userId, setUserId] = useState(-1)
    const[repaymentValue, setRepaymentValue] = useState(0)
    const[userBillInfo, setUserBillInfo] = useState([])
    const[showRepaymentPopUp, setShowRepaymentPopUp] = useState(false)

    function produceBillLoad(){
        axiosInstance.get(BACKEND_API_URL+"/api/bill", {params:{
            billId: parseInt(billId),
            accountId: parseInt(accountId)
        }})
        .then(res=>{
            if(res.status === 200){
                setBillData(res.data)
                setLoaded(true)
            }
        })
    }


    function changePopUpState(){
        setShowRepaymentPopUp(!showRepaymentPopUp)
    }

    const axiosInstance = axios.create({
        withCredentials: true,
        // headers:{'Content-Type': 'application/json'}
    })

    function produceRepayment(){
        axiosInstance.post(BACKEND_API_URL+"/api/repayment",{
            accountId: accountId,
            billId: billId,
            value: repaymentValue
        })
        .then(res=>{
            if(res.status === 200){
               navigate(0)
            }
        })
    }

    useEffect(()=>{
        if(userId!==-1 && isLoaded){
            setUserBillInfo(billData.billInfo.filter(billInfo=> billInfo.user.userId === userId)[0])
        }
        if(!isLoaded){
            produceBillLoad()
        }
    }, [isLoaded, userId])

    function filterInputField(event){
        if(isNumeric(event.target.value) || event.target.value === ""){
            if(event.target.value > userBillInfo.debt){
                event.target.value = userBillInfo.debt
            }
            else if(event.target.value < 0){
                event.target.value = 0
            }
            setRepaymentValue(event.target.value)
        }
        else{
            event.target.value = repaymentValue
        }
    }

    return(
        <Box className = "billContainer" sx={{width:"100%", height:"100vh"}}>
            <Dialog onClose={()=>{setShowRepaymentPopUp(false)}} open={showRepaymentPopUp}>
                <DialogTitle>
                    Погашение долга
                </DialogTitle>
                <DialogContent>
                    <Input onChange={filterInputField} placeholder="Сумма"/>
                    <Button onClick={produceRepayment}>
                        Погасить
                    </Button>
                </DialogContent>
            </Dialog>
            <Header updateUserId = {setUserId} updateAuthStatus={props.updateAuthStatus}/>
            {isLoaded?
            <React.Fragment>
                <BackButton/>
                <BillCard billInfo={billData}/>
                <Card sx={{borderRadius: "20px", marginTop:"2%", margin:"2%", boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
                    <Box sx={{width:'100%', display: 'flex', justifyContent: 'space-between'}}>
                        <CardContent>
                            <BillInfoTable openPopUp={changePopUpState} userId={userId} useLink = {false} billInfo={billData.billInfo}/>
                        </CardContent>
                    </Box>
                </Card>
            </React.Fragment>
            :null}
       </Box>
    )
}