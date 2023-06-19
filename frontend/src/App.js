import { useEffect, useState } from "react";
import Auth from "./Auth";
import React from "react";
import Dashboard from "./Dashboard";

function App() {

  const[isAuth, setAuth] = useState(false);

 function updateAuthStatus(status){
  if(status){
    var time = new Date(Date.now())
    time.setMinutes(time.getMinutes() + 180)
    document.cookie = "auth=true;expires="+time+"; path="/"; domain=localhost; secure"
  }
  else{
    document.cookie = "auth=false"
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
    <React.Fragment>
    {!isAuth?
    <Auth updateAuthStatus={updateAuthStatus}/>:
    <Dashboard updateAuthStatus={updateAuthStatus}/>
    }
    </React.Fragment>
  )
}

export default App;
