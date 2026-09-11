/**
 * Fetch methodes for secret api calls
 * @author Peter Rutschmann
 */

// Hilfsfunktion zur URL-Generierung
const getBaseUrl = () => {
    const protocol = process.env.REACT_APP_API_PROTOCOL || "http";
    const host = process.env.REACT_APP_API_HOST || "localhost";
    const port = process.env.REACT_APP_API_PORT || "8080";
    const path = process.env.REACT_APP_API_PATH || "/api";
    const portPart = port ? `:${port}` : '';
    return `${protocol}://${host}${portPart}${path}`;
};

// Post secret to server
export const postSecret = async ({loginValues, content, token}) => {
    const API_URL = getBaseUrl();
    try {
        const response = await fetch(`${API_URL}/secrets`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                email: loginValues.email,
                encryptPassword: loginValues.password,
                content: content
            })
        });

        const text = await response.text();
        let data = {};
        try {
            data = text ? JSON.parse(text) : {};
        } catch (e) {
            data = { message: text };
        }

        if (!response.ok) throw new Error(data.message || 'Speichern fehlgeschlagen');
        return data;
    } catch (error) {
        throw new Error(error.message);
    }
};

// Get all secrets for a user
export const getSecretsforUser = async ({loginValues, token}) => {
    const API_URL = getBaseUrl();
    try {
        const response = await fetch(`${API_URL}/secrets/byemail`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                email: loginValues.email,
                encryptPassword: loginValues.password
            })
        });

        const text = await response.text();
        if (!response.ok) {
            let errorMsg = 'Abruf fehlgeschlagen';
            try {
                const errorObj = JSON.parse(text);
                errorMsg = errorObj.message || errorMsg;
            } catch (e) {
                errorMsg = text || errorMsg;
            }
            throw new Error(errorMsg);
        }

        try {
            return JSON.parse(text);
        } catch (e) {
            console.error("Server antwortete nicht mit validem JSON:", text);
            return [];
        }
    } catch (error) {
        throw new Error(error.message);
    }
};

// Update existing secret
export const updateSecret = async ({loginValues, secretId, content, token}) => {
    const API_URL = getBaseUrl();
    try {
        const response = await fetch(`${API_URL}/secrets/${secretId}`, {
            method: 'PUT',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                email: loginValues.email,
                encryptPassword: loginValues.password,
                content: content
            })
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || 'Update fehlgeschlagen');
        }
        return true;
    } catch (error) {
        throw new Error(error.message);
    }
};

// Delete secret
export const deleteSecret = async ({loginValues, secretId, token}) => {
    const API_URL = getBaseUrl();
    try {
        const response = await fetch(`${API_URL}/secrets/${secretId}?email=${encodeURIComponent(loginValues.email)}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || 'Löschen fehlgeschlagen');
        }
        return true;
    } catch (error) {
        throw new Error(error.message);
    }
};