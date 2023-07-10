import { Box, Typography, Avatar } from "@mui/material"
import React from "react"
import BillDivederField from "../Bill/BillDividerField"

export default function UserLabel(props) {
    let user = props.user
    return (
        <Box display={"flex"} justifyContent={'space-between'} padding={'1%'} boxShadow={'0 2px 5px rgba(0,0,0,0.5)'} border={'1px solid'} borderColor={'rgb(170,170,170)'} borderRadius={'20px'}>
            <Box display={'flex'} gap={1}>
                <Avatar src={user.imgLink} />
                <Typography>{user.name} {user.surname}</Typography>
            </Box>
            <BillDivederField id={user.userId} type={props.type} paymentState={props.paymentState} paymentSetter={props.paymentSetter} updatePrice={props.updatePrice} fullPrice={props.fullPrice} />
        </Box>
    )
}