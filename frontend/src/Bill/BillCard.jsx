import { Box, Card, CardContent, Typography, CardActionArea} from "@mui/material";
import { Link } from "react-router-dom";
import React from "react"

export default function BillCard(props){
    let billInfo = props.billInfo
    let useLink = true
    if(!props.useLink)
        useLink = false

    return(
        <React.Fragment>
                    {useLink?
            <Link to = {'bill/'+billInfo.billId} style={{ textDecoration: 'none'}}>
            <Card sx={{borderRadius: "20px", marginTop:"2%", margin:"2%", boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
                <CardActionArea sx={{width:'100%', display: 'flex', justifyContent: 'space-between'}}>
                    <CardContent>
                        <Typography sx={{fontSize:'2.5vw'}}><b>{billInfo.title}</b></Typography>
                        <Typography sx={{fontSize:'1.8vw'}}>{billInfo.description}</Typography>
                    </CardContent>
                    <CardContent>
                    <Typography sx={{fontSize:'2.5vw', textAlign:'end'}}><b>Сумма: {billInfo.fullPrice}</b> ₽</Typography>
                    {billInfo.owed !== 0?
                    <Typography sx={{fontSize:'1.7vw', textAlign:'end'}}><b>Остаток: {billInfo.owed}</b> ₽</Typography>:
                    <Typography sx={{fontSize:'1.7vw', textAlign:'end', color: "green"}}><b>Трата закрыта!</b></Typography>}
                    </CardContent>
                </CardActionArea>
            </Card>
            </Link>:
            <Card sx={{borderRadius: "20px", marginTop:"2%", margin:"2%", boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
            <CardActionArea sx={{width:'100%', display: 'flex', justifyContent: 'space-between'}}>
                <CardContent>
                    <Typography sx={{fontSize:'2.5vw'}}><b>{billInfo.title}</b></Typography>
                    <Typography sx={{fontSize:'1.8vw'}}>{billInfo.description}</Typography>
                </CardContent>
                <CardContent>
                    <Typography sx={{fontSize:'2.5vw', textAlign:'end'}}><b>Сумма: {billInfo.fullPrice}</b> ₽</Typography>
                    {billInfo.owed !== 0?
                    <Typography sx={{fontSize:'1.7vw', textAlign:'end'}}><b>Остаток: {billInfo.owed}</b> ₽</Typography>:
                    <Typography sx={{fontSize:'1.7vw', textAlign:'end', color: "green"}}><b>Трата закрыта!</b></Typography>}
                </CardContent>
            </CardActionArea>
            </Card>
            }
</React.Fragment>
    )
}