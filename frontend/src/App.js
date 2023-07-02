import { useEffect, useState } from "react";
import Auth from "./Auth";
import React from "react";
import { Route, Routes, useNavigate} from "react-router-dom";
import Dashboard from "./Dashboard";
import Account from "./Account";
import axios from "axios";

function App() {

  const[isAuth, setAuth] = useState(false);
  const[userInfo, setUserInfo] = useState([]);
  const navigate = useNavigate();


 function updateAuthStatus(status){
  if(status){
    var time = new Date(Date.now())
    time.setMinutes(time.getMinutes() + 180)
    document.cookie = "auth=true;expires="+time+"; path="/"; domain=localhost; secure"
    navigate("/dashboard")
  }
  else{
    document.cookie = "auth=false"
    navigate("/login")
  }
  setAuth(status)
 }

 function getCookie(cookieName){
  const cookieValue = document.cookie
  .split('; ')
  .find((row) => row.startsWith(cookieName+'='))
  ?.split('=')[1];
  return cookieValue
}

 useEffect(()=>{
  var authCookie = getCookie("auth")
  if(typeof (authCookie)!== "undefined"){ //Check if auth cookie exist
      setAuth(authCookie === "true"? true:false)
  }
  else{
    setAuth(false)
  }
 }, [isAuth])

  return (
     <Routes>
             <Route path="/dashboard/*" element={<Dashboard updateAuthStatus = {updateAuthStatus}/>}/>
             <Route path="/login" element={<Auth updateAuthStatus = {updateAuthStatus}/>}/>
             <Route path="/dashboard/account/:id" element={<Account updateAuthStatus = {updateAuthStatus}/>}/>
     </Routes>
  )
}

export default App;
