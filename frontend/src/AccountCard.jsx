import { Box, Card, CardContent, Typography, CardActionArea} from "@mui/material";
import { Link } from "react-router-dom";

export default function AccountCard(props){
    let accountInfo = props.account
    return(
<Link to = {'account/'+props.account.accountId} style={{ textDecoration: 'none'}}>
    <Card sx={{borderRadius: "20px", marginTop:"2%", margin:"2%", boxShadow:'0 2px 5px rgba(0,0,0,0.5)'}}>
        <CardActionArea sx={{width:'100%', display: 'flex', justifyContent: 'space-between'}}>
            <CardContent>
                <Typography sx={{fontSize:'2.5vw'}}><b>{accountInfo.title}</b></Typography>
                <Typography sx={{fontSize:'1.8vw'}}>{accountInfo.description}</Typography>
            </CardContent>
            <CardContent>
                {/* <Typography sx={{fontSize:'2.5vw', textAlign:'end'}}><b>Общий долг: {billInfo.debtPrice}</b> ₽</Typography>
                {debtInfo.total!==0?<Typography sx={{fontSize:'1.8vw', color:"gray", textAlign:'end'}}>Ваша неоплаченная часть: {debtInfo.total} ₽</Typography>:
                <Typography sx={{fontSize:'1.8vw', color:"gray", textAlign:'end'}}>Ваша часть выплачена</Typography>} */}
            </CardContent>
        </CardActionArea>
    </Card>
 </Link>
    )
}