import { useNavigate } from 'react-router-dom';
import {useState} from "react";
import {postUserLogin} from "../../comunication/FetchUser";

/**
 * LoginUser
 * @author Peter Rutschmann
 */
function LoginUser({loginValues, setLoginValues}) {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        console.log(loginValues);

        try {
            await postUserLogin(loginValues);
            navigate('/');
        } catch (error) {
            console.error('Failed to fetch to server:', error.message);
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
            </form>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
}

export default LoginUser;