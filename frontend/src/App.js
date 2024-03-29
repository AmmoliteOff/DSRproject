import { useEffect, useState } from "react";
import React from "react";
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import Dashboard from "./Dashboard";
import Account from "./Account/Account";
import Auth from "./Utils/Auth";
import axios from "axios";
import UserSettings from "./Utils/UserSettings";

import Bill from "./Bill/Bill";

function App() {

  const [isAuth, setAuth] = useState(false);
  const [userInfo, setUserInfo] = useState([]);
  const navigate = useNavigate();


  function updateAuthStatus(status) {
    if (status) {
      var time = new Date(Date.now())
      time.setMinutes(time.getMinutes() + 180)
      document.cookie = "auth=true;expires=" + time + "; path=" / "; domain=localhost; secure"
      navigate("/dashboard")
    }
    else {
      document.cookie = "auth=false"
      navigate("/login")
    }
    setAuth(status)
  }

  function getCookie(cookieName) {
    const cookieValue = document.cookie
      .split('; ')
      .find((row) => row.startsWith(cookieName + '='))
      ?.split('=')[1];
    return cookieValue
  }

  useEffect(() => {
    var authCookie = getCookie("auth")
    if (typeof (authCookie) !== "undefined") { //Check if auth cookie exist
      setAuth(authCookie === "true" ? true : false)
    }
    else {
      setAuth(false)
    }
  }, [isAuth])

  return (
    
    <Routes>
      <Route index path="/dashboard" element={<Dashboard updateAuthStatus={updateAuthStatus} />} />
      <Route path="/login" element={<Auth updateAuthStatus={updateAuthStatus} />} />
      <Route path="/dashboard/account/:id" element={<Account updateAuthStatus={updateAuthStatus} />} />
      <Route path="/dashboard/account/:accountId/bill/:billId" element={<Bill updateAuthStatus={updateAuthStatus} />} />
      <Route path="/settings" element={<UserSettings />} />
      <Route path="*" element={<Navigate to="/dashboard" replace/>}/>
    </Routes>
  )
}

export default App;
