import { Box, Card, CardContent, Typography, CardActionArea, Avatar, AvatarGroup } from "@mui/material";
import { Link } from "react-router-dom";

export default function AccountCard(props) {
    let accountInfo = props.account
    return (
        <Link to={'account/' + props.account.accountId} style={{ textDecoration: 'none' }}>
            <Card sx={{ borderRadius: "20px", marginTop: "2%", margin: "2%", boxShadow: '0 2px 5px rgba(0,0,0,0.5)' }}>
                <CardActionArea sx={{ width: '100%', display: 'flex', justifyContent: 'space-between' }}>
                    <CardContent>
                        <Typography sx={{ fontSize: '2.5vw' }}><b>{accountInfo.title}</b></Typography>
                        <Typography sx={{ fontSize: '1.8vw' }}>{accountInfo.description}</Typography>
                    </CardContent>
                    <CardContent>
                        <AvatarGroup max={5}>
                            {accountInfo.users.map(user => {
                                return (
                                    <Avatar key={user.userId} alt={user.name + " " + user.surname} src={user.imgLink} />
                                )
                            })}
                        </AvatarGroup>
                    </CardContent>
                </CardActionArea>
            </Card>
        </Link>
    )
}