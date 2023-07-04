import { Button } from "@mui/material"
import { useParams, useNavigate} from "react-router-dom"

export default function BackButton(){
 const navigate = useNavigate()

 return(<Button onClick={()=>navigate(-1)}>
    Назад
 </Button>)
}