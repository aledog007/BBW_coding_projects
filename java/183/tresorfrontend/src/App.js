import React, {useState} from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import './App.css';
import './css/mvp.css';
import Home from './pages/Home';
import Layout from "./pages/Layout";
import NoPage from "./pages/NoPage";
import Users from './pages/user/Users';
import LoginUser from "./pages/user/LoginUser";
import RegisterUser from "./pages/user/RegisterUser";
import Secrets from "./pages/secret/Secrets";
import NewCredential from "./pages/secret/NewCredential";
import NewCreditCard from "./pages/secret/NewCreditCard";
import NewNote from "./pages/secret/NewNote";
import ForgotPassword from "./pages/user/ForgotPassword";
import ResetPassword from "./pages/user/ResetPassword";
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Verify2FA from './pages/auth/Verify2FA';
import Setup2FA from './pages/auth/Setup2FA';
import OAuth2Callback from './pages/auth/OAuth2Callback';

/**
 * App
 * @author Peter Rutschmann
 */
function App() {
    const [loginValues, setLoginValues] = useState({
        email: "",
        password: "",
    });
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Layout loginValues={loginValues}/>}>
                        <Route index element={<Home/>}/>
                        
                        {/* Public Routes */}
                        <Route path="/user/login" element={<LoginUser loginValues={loginValues} setLoginValues={setLoginValues}/>}/>
                        <Route path="/user/register" element={<RegisterUser loginValues={loginValues} setLoginValues={setLoginValues}/>}/>
                        <Route path="/forgot-password" element={<ForgotPassword />} />
                        <Route path="/reset-password/:token" element={<ResetPassword />} />
                        <Route path="/verify-2fa" element={<Verify2FA />} />
                        <Route path="/oauth2/callback" element={<OAuth2Callback />} />
                        
                        {/* Admin Routes */}
                        <Route path="/user/users" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><Users loginValues={loginValues}/></ProtectedRoute>}/>
                        
                        {/* Protected Routes */}
                        <Route path="/secret/secrets" element={<ProtectedRoute><Secrets loginValues={loginValues}/></ProtectedRoute>}/>
                        <Route path="/secret/newcredential" element={<ProtectedRoute><NewCredential loginValues={loginValues}/></ProtectedRoute>}/>
                        <Route path="/secret/newcreditcard" element={<ProtectedRoute><NewCreditCard loginValues={loginValues}/></ProtectedRoute>}/>
                        <Route path="/secret/newnote" element={<ProtectedRoute><NewNote loginValues={loginValues}/></ProtectedRoute>}/>
                        <Route path="/setup-2fa" element={<ProtectedRoute><Setup2FA /></ProtectedRoute>} />
                        
                        <Route path="*" element={<NoPage/>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    )
}

export default App;