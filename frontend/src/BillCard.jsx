import { Box, Card, CardContent, Typography, CardActionArea} from "@mui/material";

export default function BillCard(props){
    let billInfo = props.bill
    return(
 <Card sx={{borderRadius: "20px", marginTop:"2%", margin:"2%", boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
    <CardActionArea sx={{width:'100%', display: 'flex', justifyContent: 'space-between'}}>
        <CardContent>
            <Typography sx={{fontSize:'2.5vw'}}><b>{billInfo.title}</b></Typography>
            <Typography sx={{fontSize:'1.8vw'}}>{billInfo.description}</Typography>
        </CardContent>
        <CardContent>
           <Typography sx={{fontSize:'2.5vw', textAlign:'end'}}><b>Общий долг: {billInfo.fullPrice/100.0}</b> ₽</Typography>
            {/* {debtInfo.total!==0?<Typography sx={{fontSize:'1.8vw', color:"gray", textAlign:'end'}}>Ваша неоплаченная часть: {debtInfo.total} ₽</Typography>:
            <Typography sx={{fontSize:'1.8vw', color:"gray", textAlign:'end'}}>Ваша часть выплачена</Typography>} */}
        </CardContent>
    </CardActionArea>
 </Card>
    )
}