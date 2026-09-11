import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

function Verify2FA() {
    const location = useLocation();
    const navigate = useNavigate();
    const { verify2FA } = useAuth();
    
    const [code, setCode] = useState('');
    const [error, setError] = useState('');

    const tempToken = location.state?.tempToken;
    const password = location.state?.password;

    if (!tempToken) {
        return <p>Kein Token vorhanden. Bitte neu anmelden.</p>;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await verify2FA(tempToken, code, password);
            navigate('/');
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div>
            <h2>2FA Verifizierung</h2>
            <form onSubmit={handleSubmit}>
                <label>Code aus Authenticator-App:</label>
                <input 
                    type="text" 
                    value={code} 
                    onChange={e => setCode(e.target.value)} 
                    required 
                />
                <button type="submit">Verifizieren</button>
            </form>
            {error && <p style={{color: 'red'}}>{error}</p>}
        </div>
    );
}

export default Verify2FA;
