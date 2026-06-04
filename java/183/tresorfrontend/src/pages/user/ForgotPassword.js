import React, { useState } from 'react';
import { postForgotPassword } from '../../comunication/FetchUser';

function ForgotPassword() {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('');
        setError('');
        try {
            const data = await postForgotPassword(email);
            setMessage(data.message);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div>
            <h2>Passwort vergessen</h2>
            <form onSubmit={handleSubmit}>
                <section>
                    <aside>
                        <div>
                            <label>E-Mail:</label>
                            <input
                                type="text"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                placeholder="Bitte E-Mail eingeben"
                            />
                        </div>
                    </aside>
                </section>
                <button type="submit">Reset-Link senden</button>
            </form>
            {message && <p style={{ color: 'green' }}>{message}</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}

export default ForgotPassword;
