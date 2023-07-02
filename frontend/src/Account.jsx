import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import React from "react"
import BillCard from "./BillCard"
import { Box, Dialog, DialogTitle, Button, Typography, Input, FormControl, RadioGroup, FormControlLabel, Radio, FormLabel} from "@mui/material"
import axios from "axios"
import Header from "./Header"

export default function Account(props){

    const[accountInfo, setAccountInfo] = useState()
    const[isLoaded, setLoaded] = useState(false)
    const {id} = useParams()
    const[popUpState, setPopUpState] = useState(false)
    const[title, setTitle] = useState("")
    const[description, setDescription] = useState("")
    const[chosenType, setChosenType] = useState(0)

    const axiosInstance = axios.create({
        withCredentials: true
    })

    function produceAccountBillsLoad(){
        axiosInstance.get("http://localhost:8080/api/account", { params: { accountId: id } }).then(res=>{
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
        setChosenType(event.target.value)
    }

    useEffect(()=>{
        if(!isLoaded){
            produceAccountBillsLoad()
        }
    }, [])

    function updateDescription(event){
        setDescription(event.target.value)
    }

    function updateTitle(event){
        setTitle(event.target.value)
    }

    return(
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

                    </Box>
                        <Button >Создать трату</Button>
                </Box>
            </Dialog>

          <Box className = "accountContainer" sx={{width:"100%", height:"100vh"}}>
            <Header updateAuthStatus={props.updateAuthStatus}/>
            <Button onClick={changePopUpState}>Создать трату</Button>
            {isLoaded?
            <React.Fragment>
                {accountInfo.bills.map(obj=>{
                    return(
                        <BillCard key={obj.billId} bill={obj}/>
                    )
                })}
            </React.Fragment>
                :null}
          </Box>
        </React.Fragment>
    )
}