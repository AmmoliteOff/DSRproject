import React from "react"
import { Box, Avatar, Typography, Input, Button} from "@mui/material"

export default function BillInfoTable(props){
    let billInfos = props.billInfo

    return(
            <Box display={"flex"} flexDirection={"column"}>
            {billInfos.map(billInfo=>{
                return(
                    <Box key = {billInfo.billInfoId} display={"flex"} flexDirection={"row"}>
                                                            <Avatar src={billInfo.user.imgLink}/>
                                    <Typography margin={"10px"}>{billInfo.user.name}</Typography>
                                    <Typography margin={"10px"}>{billInfo.user.surname}</Typography>
                                    {billInfo.debt !== 0?
                                    <React.Fragment>
                                        <Typography margin={"10px"}>{billInfo.debt}₽</Typography>
                                        {billInfo.user.userId === props.userId?
                                            <Button onClick={()=>props.openPopUp()}>
                                                Погасить долг
                                            </Button>
                                        :null}
                                    </React.Fragment>:
                                    <Typography margin={"10px"} color={"green"}>Долг выплачен</Typography>}
                    </Box>
                )
            })}
            </Box>
    )
}