import { useNavigate } from 'react-router-dom';
import {useState} from "react";
import { useAuth } from '../../context/AuthContext';

/**
 * LoginUser
 * @author Peter Rutschmann
 */
function LoginUser({loginValues, setLoginValues}) {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const { login } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        try {
            const result = await login(loginValues.email, loginValues.password);
            if (result.requires2FA) {
                navigate('/verify-2fa', { state: { tempToken: result.tempToken, password: loginValues.password } });
            } else {
                navigate('/');
            }
        } catch (error) {
            console.error('Failed to login:', error.message);
            setErrorMessage(error.message);
        }
    };

    return (
        <div>
            <h2>Benutzer Login</h2>
            <form onSubmit={handleSubmit}>
                <section>
                    <aside>
                        <div>
                            <label>E-Mail:</label>
                            <input
                                type="text"
                                value={loginValues.email}
                                onChange={(e) =>
                                    setLoginValues(prevValues => ({...prevValues, email: e.target.value}))}
                                required
                                placeholder="Bitte E-Mail eingeben *"
                            />
                        </div>
                        <div>
                            <label>Passwort:</label>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={loginValues.password}
                                    onChange={(e) =>
                                        setLoginValues(prevValues => ({...prevValues, password: e.target.value}))}
                                    required
                                    placeholder="Bitte Passwort eingeben *"
                                    style={{ flex: 1 }}
                                />
                                <button type="button" onClick={() => setShowPassword(!showPassword)} style={{ marginLeft: '5px' }}>
                                    {showPassword ? '🙈' : '👁️'}
                                </button>
                            </div>
                        </div>
                    </aside>
                </section>
                <button type="submit">Anmelden</button>
                <div style={{ marginTop: '10px' }}>
                    <button type="button" onClick={() => navigate('/forgot-password')}>Passwort vergessen?</button>
                </div>
                <hr style={{ marginTop: '20px', marginBottom: '20px' }}/>
                <div style={{ marginTop: '10px', textAlign: 'center' }}>
                    <a href="http://localhost:8080/oauth2/authorization/google" 
                       style={{
                           display: 'inline-block',
                           padding: '10px 20px',
                           backgroundColor: '#4285F4',
                           color: 'white',
                           textDecoration: 'none',
                           borderRadius: '5px',
                           fontWeight: 'bold'
                       }}>
                        Mit Google einloggen
                    </a>
                </div>
            </form>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
}

export default LoginUser;