import React, { useState } from 'react';
import { useAuth, getBaseUrl } from '../../context/AuthContext';

function Setup2FA() {
    const { accessToken, authFetch } = useAuth();
    const [qrCodeImage, setQrCodeImage] = useState('');
    const [code, setCode] = useState('');
    const [message, setMessage] = useState('');

    const generate2FA = async () => {
        try {
            const res = await fetch(`${getBaseUrl()}/auth/2fa/setup`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                }
            });
            const data = await res.json();
            if (res.ok) {
                setQrCodeImage(data.qrCodeUrl);
            } else {
                setMessage(data.message || 'Fehler beim Generieren des QR Codes');
            }
        } catch (err) {
            setMessage('Fehler beim Verbinden mit dem Server');
        }
    };

    const enable2FA = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch(`${getBaseUrl()}/auth/2fa/enable`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}`
                },
                body: JSON.stringify({ code })
            });
            const text = await res.text();
            if (res.ok) {
                setMessage('2FA wurde erfolgreich aktiviert!');
                setQrCodeImage('');
            } else {
                setMessage(text || 'Code ungültig');
            }
        } catch (err) {
            setMessage('Fehler beim Aktivieren');
        }
    };

    return (
        <div>
            <h2>2FA Setup</h2>
            <button onClick={generate2FA}>QR Code für 2FA generieren</button>
            {qrCodeImage && (
                <div>
                    <img src={qrCodeImage} alt="QR Code für Google Authenticator" />
                    <form onSubmit={enable2FA}>
                        <label>Verifizierungscode:</label>
                        <input 
                            type="text" 
                            value={code} 
                            onChange={(e) => setCode(e.target.value)} 
                            required 
                        />
                        <button type="submit">Aktivieren</button>
                    </form>
                </div>
            )}
            {message && <p>{message}</p>}
        </div>
    );
}

export default Setup2FA;
