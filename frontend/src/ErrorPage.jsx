import { Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function ErrorPage(){

    const navigate = useNavigate()

    return(
        <Box width={'100vw'} height={'100vh'} display={'flex'} justifyContent={'center'} alignItems={'center'}>
            <Box display={'flex'} flexDirection={'column'} gap={1}>
                <Typography textAlign={'center'} fontSize={'5.5vh'}>
                    {"К сожалению, у нас не получилось загрузить страницу"}
                </Typography>
                <Box display={'flex'} justifyContent={'center'}>
                    <Button onClick={()=>{navigate(0)}} variant="outlined" color="error" sx={{fontSize: "5.5vh"}}>
                        Попробовать снова
                    </Button>
                </Box>
            </Box>
        </Box>

    )
}