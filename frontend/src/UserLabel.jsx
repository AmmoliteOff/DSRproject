import { Box, Typography, Avatar } from "@mui/material"
 import React from "react"
import BillDivederField from "./BillDividerField"

export default function UserLabel(props){
    let user = props.user
    return(
<Box display={"flex"} flexDirection={"row"}>
                                    <Avatar src={user.imgLink}/>
                                    <Typography margin={"10px"}>{user.name}</Typography>
                                    <Typography margin={"10px"}>{user.surname}</Typography>
                                    <BillDivederField margin={"10px"} id={user.userId} type={props.type} paymentState = {props.paymentState} paymentSetter={props.paymentSetter} updatePrice={props.updatePrice} fullPrice={props.fullPrice}/>
                                </Box>
    )
}