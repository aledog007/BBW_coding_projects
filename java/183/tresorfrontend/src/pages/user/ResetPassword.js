import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { postResetPassword } from '../../comunication/FetchUser';
import { evaluatePasswordStrength } from '../../util/PasswordStrength';
import { checkPwnedPassword, generateSecurePassword } from '../../util/PasswordPwnedCheck';

function ResetPassword() {
    const { token } = useParams();
    const navigate = useNavigate();

    const [credentials, setCredentials] = useState({
        password: '',
        passwordConfirmation: ''
    });
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [strength, setStrength] = useState(0);
    const [pwnedCount, setPwnedCount] = useState(0);
    const [showPassword, setShowPassword] = useState(false);

    const handlePasswordChange = async (e) => {
        const val = e.target.value;
        setCredentials(prev => ({...prev, password: val}));
        setStrength(evaluatePasswordStrength(val));
        const count = await checkPwnedPassword(val);
        setPwnedCount(count);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('');
        setError('');

        if(credentials.password !== credentials.passwordConfirmation) {
            setError('Passwörter stimmen nicht überein.');
            return;
        }

        try {
            const data = await postResetPassword({
                token: token,
                password: credentials.password,
                passwordConfirmation: credentials.passwordConfirmation
            });
            setMessage(data.message);
            setTimeout(() => navigate('/user/login'), 3000);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div>
            <h2>Passwort zurücksetzen</h2>
            <form onSubmit={handleSubmit}>
                <section>
                    <aside>
                        <div>
                            <label>Neues Passwort:</label>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={credentials.password}
                                    onChange={handlePasswordChange}
                                    required
                                    placeholder="Bitte neues Passwort eingeben *"
                                    style={{ flex: 1 }}
                                />
                                <button type="button" onClick={() => setShowPassword(!showPassword)} style={{ marginLeft: '5px' }}>
                                    {showPassword ? '🙈' : '👁️'}
                                </button>
                            </div>
                            <div style={{ marginTop: '5px' }}>
                                <button type="button" onClick={() => {
                                    const newPwd = generateSecurePassword();
                                    setCredentials(prev => ({...prev, password: newPwd, passwordConfirmation: newPwd}));
                                    setStrength(evaluatePasswordStrength(newPwd));
                                    setPwnedCount(0);
                                }}>Sicheres Passwort generieren</button>
                            </div>
                            {pwnedCount > 0 && <div style={{ color: 'red', marginTop: '5px', fontWeight: 'bold' }}>
                                ACHTUNG: Dieses Passwort wurde bereits {pwnedCount} Mal in Daten-Leaks gefunden. Bitte wähle ein anderes!
                            </div>}
                            <div style={{ marginTop: '5px' }}>
                                <span>Stärke: {strength}%</span>
                                <div style={{ background: '#eee', height: '10px', width: '100%' }}>
                                    <div style={{ background: strength > 80 ? 'green' : strength > 40 ? 'orange' : 'red', height: '100%', width: `${strength}%` }} />
                                </div>
                            </div>
                        </div>
                        <div>
                            <label>Passwort bestätigen:</label>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={credentials.passwordConfirmation}
                                    onChange={(e) => setCredentials(prev => ({...prev, passwordConfirmation: e.target.value}))}
                                    required
                                    placeholder="Bitte neues Passwort bestätigen *"
                                    style={{ flex: 1 }}
                                />
                                <button type="button" onClick={() => setShowPassword(!showPassword)} style={{ marginLeft: '5px' }}>
                                    {showPassword ? '🙈' : '👁️'}
                                </button>
                            </div>
                        </div>
                    </aside>
                </section>
                <button type="submit">Passwort speichern</button>
            </form>
            {message && <p style={{ color: 'green' }}>{message}</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}

export default ResetPassword;
