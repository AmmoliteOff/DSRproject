import { Input, Typography, Box } from "@mui/material"
import { useState } from "react"

export default function BillDivederField(props){

    const[lastPercentField, setLastPercentField] = useState("")
    const[lastManualField, setLastManualField] = useState("")

    let type = parseInt(props.type)
    let id = props.id

    function isNumeric(str) {
        if (typeof str != "string") return false
        return !isNaN(str) &&
               !isNaN(parseFloat(str))
      }

    function manualChange(event){
        let val = parseInt(event.target.value)
        if(isNumeric(event.target.value)){
            if(val >= 0){
                var c = props.paymentState
                if(c.filter(obj => obj.id === id).length === 0){
                    c.push({id: id, debt: val})
                    props.paymentSetter(c)
                }
                else{
                    for(let i=0; i<props.paymentState.length; i++){
                        if(c[i].id === c.filter(obj => obj.id === id)[0].id){
                            c[i] = {id: id, debt: val}
                        }
                    }
                    props.paymentSetter(c)
                }
                setLastManualField(event.target.value)
            }
        else{
                event.target.value = "0"
        }
        }
        else{
            if(event.target.value!==""){
            event.target.value = lastManualField
            }
            else{
                setLastManualField(event.target.value)
            }
        }
        props.updatePrice()
    }

    function percentChange(event){
        let val = parseFloat(event.target.value)
        let upperBond = 100
        console.log(props.paymentState)
        if(props.paymentState.length!==0){
            for(let i = 0; i<props.paymentState.length; i++){
                if(props.paymentState[i].id!==id)
                upperBond-=props.paymentState[i].debt
            }
        }

        console.log(upperBond)

        if(isNumeric(event.target.value)){
            if(val >= 0 && val<=upperBond){
                var c = props.paymentState
                if(c.filter(obj => obj.id === id).length === 0){
                    c.push({id: id, debt: val})
                    props.paymentSetter(c)
                }
                else{
                    for(let i=0; i<props.paymentState.length; i++){
                        if(c[i].id === c.filter(obj => obj.id === id)[0].id){
                            c[i] = {id: id, debt: val}
                        }
                    }
                    props.paymentSetter(c)
                }
                setLastPercentField(event.target.value)
            }
        else{
            if(val<0){
                event.target.value = "0"
                var copy = props.paymentState
                for(let i = 0; i<copy.length; i++){
                    if(copy[i].id === id)
                        copy[i].debt = 0
                }
                props.paymentSetter(copy)
            }
            else{
                event.target.value = upperBond
                var copy = props.paymentState
                for(let i = 0; i<copy.length; i++){
                    if(copy[i].id === id)
                        copy[i].debt = upperBond
                }
                props.paymentSetter(copy)
            }
        }
        }
        else{
            if(event.target.value!==""){
            event.target.value = lastPercentField
            }
        }
    }
    

    switch(type){
        case 0:
            return(null)
        case 1:
            return(
                <Box display={"flex"} flexDirection={"row"}>
            <Box marginRight={'2%'}>
                <Input onChange={percentChange} placeholder="Процент"/>  
            </Box>

            {props.paymentState.filter(obj => obj.id === id).length!==0?
            <Typography>({props.fullPrice*props.paymentState.filter(obj => obj.id === id)[0].debt/100.0}₽)</Typography>:
            null}
            </Box>)
        case 2: 
            return(<Input onChange={manualChange} placeholder="Сумма">
            </Input>)
    }
}