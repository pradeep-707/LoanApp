import React, { useEffect, useState } from 'react'
import AboutUsComponent from './AboutUsComponent'
import HomeComponent from './HomeComponent'
import Register from './Register'
import { Routes, Route } from 'react-router-dom'
import NotFoundComponent from './NotFoundComponent';
import Login from './Login'
import NavbarComponent from './NavbarComponent'

const MainComponent = () => {

  const [user,setUser] = useState("");

  useEffect(() => {
    const username = sessionStorage.getItem("username");
    setUser(username)
  },[])

  const loginUser = (username) =>{
    sessionStorage.setItem("username",username);
    setUser(username)
  }

  const logoutUser = () =>{
    sessionStorage.removeItem("username");
    setUser("")
  }

  return (
      <div>
        <NavbarComponent user={user} loginUser = {loginUser} logoutUser={logoutUser}/>
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Routes>
              <Route path="/" element={<HomeComponent user={user} />} />
              <Route path="/about" element={<AboutUsComponent />} />
              <Route path="/register" element={<Register  user={user} loginUser = {loginUser}/>} />
              <Route path="/login" element={<Login  user={user} loginUser = {loginUser}/>} />
              <Route path="*" element={<NotFoundComponent />} />
            </Routes>
          </div>
        </div>
      </div>
    
    
  )

}

export default MainComponent