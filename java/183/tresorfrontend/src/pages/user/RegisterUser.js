import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {postUser} from "../../comunication/FetchUser";
import ReCAPTCHA from 'react-google-recaptcha';
import { evaluatePasswordStrength } from '../../util/PasswordStrength';
import { checkPwnedPassword, generateSecurePassword } from '../../util/PasswordPwnedCheck';

/**
 * RegisterUser
 * @author Peter Rutschmann
 */
function RegisterUser({loginValues, setLoginValues}) {
    const navigate = useNavigate();

    const initialState = {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        passwordConfirmation: "",
        recaptchaToken: "",
        errorMessage: ""
    };
    const [credentials, setCredentials] = useState(initialState);
    const [errorMessage, setErrorMessage] = useState('');
    const [passwordStrength, setPasswordStrength] = useState(0);
    const [pwnedCount, setPwnedCount] = useState(0);
    const [showPassword, setShowPassword] = useState(false);

    const handlePasswordChange = async (e) => {
        const val = e.target.value;
        setCredentials(prevValues => ({...prevValues, password: val}));
        setPasswordStrength(evaluatePasswordStrength(val));
        const count = await checkPwnedPassword(val);
        setPwnedCount(count);
    };

    const handleRecaptcha = (token) => {
        setCredentials(prevValues => ({...prevValues, recaptchaToken: token}));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        //validate
        if(credentials.password !== credentials.passwordConfirmation) {
            console.log("password != passwordConfirmation");
            setErrorMessage('Passwörter stimmen nicht überein.');
            return;
        }

        if (!credentials.recaptchaToken) {
            setErrorMessage('Bitte Captcha (Ich bin kein Roboter) lösen.');
            return;
        }

        try {
            await postUser(credentials);
            setLoginValues({userName: credentials.email, password: credentials.password});
            setCredentials(initialState);
            navigate('/');
        } catch (error) {
            console.error('Failed to fetch to server:', error.message);
            setErrorMessage(error.message);
        }
    };

    return (
        <div>
            <h2>Benutzer registrieren</h2>
            <form onSubmit={handleSubmit}>
                <section>
                <aside>
                    <div>
                        <label>Vorname:</label>
                        <input
                            type="text"
                            value={credentials.firstName}
                            onChange={(e) =>
                                setCredentials(prevValues => ({...prevValues, firstName: e.target.value}))}
                            required
                            placeholder="Bitte Vornamen eingeben *"
                        />
                    </div>
                    <div>
                        <label>Nachname:</label>
                        <input
                            type="text"
                            value={credentials.lastName}
                            onChange={(e) =>
                                setCredentials(prevValues => ({...prevValues, lastName: e.target.value}))}
                            required
                            placeholder="Bitte Nachnamen eingeben *"
                        />
                    </div>
                    <div>
                        <label>E-Mail:</label>
                        <input
                            type="text"
                            value={credentials.email}
                            onChange={(e) =>
                                setCredentials(prevValues => ({...prevValues, email: e.target.value}))}
                            required
                            placeholder="Bitte E-Mail eingeben *"
                        />
                    </div>
                </aside>
                    <aside>
                        <div>
                            <label>Passwort:</label>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={credentials.password}
                                    onChange={handlePasswordChange}
                                    required
                                    placeholder="Bitte Passwort eingeben *"
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
                                    setPasswordStrength(evaluatePasswordStrength(newPwd));
                                    setPwnedCount(0);
                                }}>Sicheres Passwort generieren</button>
                            </div>
                            {pwnedCount > 0 && <div style={{ color: 'red', marginTop: '5px', fontWeight: 'bold' }}>
                                ACHTUNG: Dieses Passwort wurde bereits {pwnedCount} Mal in Daten-Leaks gefunden. Bitte wähle ein anderes!
                            </div>}
                            <div style={{ marginTop: '5px' }}>
                                <span>Stärke: {passwordStrength}%</span>
                                <div style={{ background: '#eee', height: '10px', width: '100%' }}>
                                    <div style={{ background: passwordStrength > 80 ? 'green' : passwordStrength > 40 ? 'orange' : 'red', height: '100%', width: `${passwordStrength}%` }} />
                                </div>
                            </div>
                        </div>
                        <div>
                            <label>Passwort Bestätigung:</label>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={credentials.passwordConfirmation}
                                    onChange={(e) =>
                                        setCredentials(prevValues => ({...prevValues, passwordConfirmation: e.target.value}))}
                                    required
                                    placeholder="Bitte Passwort bestätigen *"
                                    style={{ flex: 1 }}
                                />
                                <button type="button" onClick={() => setShowPassword(!showPassword)} style={{ marginLeft: '5px' }}>
                                    {showPassword ? '🙈' : '👁️'}
                                </button>
                            </div>
                        </div>
                    </aside>
                </section>
                <div style={{ marginBottom: '15px' }}>
                    <ReCAPTCHA
                        sitekey={process.env.REACT_APP_RECAPTCHA_SITE_KEY || "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI"}
                        onChange={handleRecaptcha}
                    />
                </div>
                <button type="submit">Registrieren</button>
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            </form>
        </div>
    );
}

export default RegisterUser;
