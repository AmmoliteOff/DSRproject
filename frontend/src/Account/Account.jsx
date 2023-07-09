import { useEffect, useState } from "react"
import { useParams, useNavigate} from "react-router-dom"
import React from "react"
import BillCard from "../Bill/BillCard"
import { Box, Dialog, DialogTitle, Button, Input, FormControl, RadioGroup, FormControlLabel, Radio, FormLabel, Typography} from "@mui/material"
import axios from "axios"
import Header from "../Utils/Header"
import UserLabel from "../Utils/UserLabel"
import { BACKEND_API_URL } from "../Utils/constants"
import BackButton from "../Utils/BackButton"

export default function Account(props){

    const navigate = useNavigate();
    const[accountInfo, setAccountInfo] = useState()
    const[isLoaded, setLoaded] = useState(false)
    const {id} = useParams()
    const[popUpState, setPopUpState] = useState(false)
    const[fullPrice, setFullPrice] = useState(0)
    const[title, setTitle] = useState("")
    const[description, setDescription] = useState("")
    const[paymentMap, setPaymentMap] = useState([])
    const[chosenType, setChosenType] = useState(0)
    const[lastFullPrice, setLastFullPrice] = useState("")
    const[buttonEnabled, setButtonEnabled] = useState(false)

    function isNumeric(str) {
        if (typeof str != "string") return false
        return !isNaN(str) &&
               !isNaN(parseFloat(str))
      }

    const axiosInstance = axios.create({
        withCredentials: true,
        headers:{'Content-Type': 'application/json'}
    })

    function produceAccountBillsLoad(){
        axiosInstance.get(BACKEND_API_URL+"/api/account", { params: { accountId: id } }).then(res=>{
            if(res.status === 200){
                setAccountInfo(res.data)
                setLoaded(true)
            }
        })
    }

    function changePopUpState(){
        setPopUpState(!popUpState)
    }

    function typeChanged(event){
        setFullPrice(0)
        setPaymentMap([])
        validateForm()
        setChosenType(parseInt(event.target.value))
    }

    function updatePrice(){
        var res = 0
        for(let i = 0; i<paymentMap.length; i++){
            res+=paymentMap[i].debt
        }
        setFullPrice(res)
        validateForm()
    }

    function validateForm(){
        if(chosenType !== 0){
            console.log(fullPrice)
            if(title!=="" && description!=="" && fullPrice!=0){
                if(paymentMap.length === accountInfo.users.length){
                    if(chosenType!==1)
                        setButtonEnabled(true)
                    else{
                        console.log("adsad")
                        var sum = 0
                        for(let i=0; i<paymentMap.length; i++){
                            sum+=paymentMap[i].debt
                        }
                        if(sum === 100){
                            setButtonEnabled(true)
                        }
                        else{
                            setButtonEnabled(false)
                        }
                    }
                }
                else{
                    setButtonEnabled(false)
                }
            }
            else{
                setButtonEnabled(false)
            }
        }
        else{
            if(title!=="" && description!=="" && fullPrice!=0){
                if(paymentMap.length === 0){
                    var c = []
                    for(let i=0; i<accountInfo.users.length; i++){
                        c.push({id:accountInfo.users[i].userId, debt:fullPrice/accountInfo.users.length})
                    }
                    setPaymentMap(c)
                }
                setButtonEnabled(true)
            }
            else{
                setButtonEnabled(false)
            }
        }
    }

    useEffect(()=>{
        validateForm()

        if(!isLoaded){
            produceAccountBillsLoad()
        }
    }, [chosenType, title, description, fullPrice, paymentMap])

    function updateDescription(event){
        setDescription(event.target.value)
    }

    function updateTitle(event){
        setTitle(event.target.value)
    }

    function changeFullPrice(event){
        if(isNumeric(event.target.value)){
            setFullPrice(event.target.value)
            setLastFullPrice(event.target.value)
        }
        else{
            if(event.target.value!=="")
                event.target.value = lastFullPrice
            else
                setLastFullPrice(event.target.value)
        }
    }

    function updatePaymentMap(c){
        setPaymentMap(c)
        validateForm()
    }

    function createBill(){

        let usersMap = []
        paymentMap.forEach(element => {
            usersMap.push({userId: element.id, debt: element.debt})
        });

        let data={
            title: title,
            description: description,
            type: chosenType,
            fullPrice: fullPrice,
            accountId: accountInfo.accountId, 
            usersMap: usersMap,
        }
        axiosInstance.post(BACKEND_API_URL+"/api/createBill", data).then(res=>{
            if(res.status === 200){
                navigate(0)
            }
        })
    }

    return(
        <React.Fragment>
{isLoaded?
        <React.Fragment>
<Dialog onClose={changePopUpState} open={popUpState}>          
                <DialogTitle>Создание траты</DialogTitle>
                <Box width={"60vw"} height={"50vh"}>
                    <Box display={"flex"} flexDirection={"column"}> 
                        <Input id="titleInput" onChange={updateTitle} placeholder="Введите название траты"/>
                        <Input id="descriptionInput" onChange={updateDescription} placeholder="Введите описание траты"/>

                                            <FormControl>
                        <FormLabel >Тип</FormLabel>
                        <RadioGroup
                            value={chosenType}
                            onChange={typeChanged}
                            row
                            aria-labelledby="demo-row-radio-buttons-group-label"
                            name="row-radio-buttons-group"
                        >
                            <FormControlLabel value={0} control={<Radio />} label="Авто" />
                            <FormControlLabel value={1} control={<Radio />} label="Процент" />
                            <FormControlLabel value={2} control={<Radio />} label="Вручную" />
                        </RadioGroup>
                        </FormControl>
                        {chosenType!==2?<Input onChange={changeFullPrice} placeholder="Полная сумма"/>:<Typography>Полная сумма: {fullPrice}</Typography>}
                        {accountInfo.users.map(user=>{
                            return(<UserLabel fullPrice = {fullPrice} key = {user.userId} user={user} type={chosenType} paymentState = {paymentMap} paymentSetter={updatePaymentMap} updatePrice={updatePrice}/>

                            )
                        })}
                    </Box>
                        <Button disabled={!buttonEnabled} onClick={createBill}>Создать трату</Button>
                </Box>
            </Dialog>

          <Box className = "accountContainer" sx={{width:"100%", height:"100vh"}}>
            <Header updateAuthStatus={props.updateAuthStatus}/>
            <BackButton/>
            <Button onClick={changePopUpState}>Создать трату</Button>
            {isLoaded?
            <React.Fragment>
                {accountInfo.bills.map(obj=>{
                    return(
                        <BillCard useLink = {true} key={obj.billId} billInfo={obj}/>
                    )
                })}
            </React.Fragment>
                :null}
          </Box>
        </React.Fragment>:null}
        </React.Fragment>
    )
}