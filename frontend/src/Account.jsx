import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import React from "react"
import BillCard from "./BillCard"
import { Box } from "@mui/material"
import axios from "axios"
import Header from "./Header"

export default function Account(props){

    const[accountInfo, setAccountInfo] = useState()
    const[isLoaded, setLoaded] = useState(false)
    const {id} = useParams()

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

    useEffect(()=>{
        if(!isLoaded){
            produceAccountBillsLoad()
        }
    }, [])

    return(
        <React.Fragment>
          <Box className = "dashboardContainer" sx={{width:"100%", height:"100vh"}}>
            <Header updateAuthStatus={props.updateAuthStatus}/>
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