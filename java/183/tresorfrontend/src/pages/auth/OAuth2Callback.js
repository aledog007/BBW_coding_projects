import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

function OAuth2Callback() {
    const navigate = useNavigate();
    const location = useLocation();
    const { loginWithOAuth } = useAuth();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const error = queryParams.get('error');
        const accessToken = queryParams.get('accessToken');
        const refreshToken = queryParams.get('refreshToken');
        const role = queryParams.get('role');
        const email = queryParams.get('email');

        if (error) {
            alert(`OAuth Error: ${error}`);
            navigate('/user/login');
            return;
        }

        if (accessToken && refreshToken) {
            loginWithOAuth(accessToken, refreshToken, role, email);
            navigate('/');
        } else {
            navigate('/user/login');
        }
    }, [location, loginWithOAuth, navigate]);

    return (
        <div>
            <h2>Anmeldung wird verarbeitet...</h2>
        </div>
    );
}

export default OAuth2Callback;
