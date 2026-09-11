import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';

/**
 * AuthContext - Hier regeln wir den ganzen Login-Kram mit JWT
 * Merkt sich die Tokens, den User und das PW für die Secrets.
 */
const AuthContext = createContext(null);

export const useAuth = () => useContext(AuthContext);

const getBaseUrl = () => {
    const protocol = process.env.REACT_APP_API_PROTOCOL || "http";
    const host = process.env.REACT_APP_API_HOST || "localhost";
    const port = process.env.REACT_APP_API_PORT || "8080";
    const path = process.env.REACT_APP_API_PATH || "/api";
    const portPart = port ? `:${port}` : '';
    return `${protocol}://${host}${portPart}${path}`;
};

export { getBaseUrl };

export function AuthProvider({ children }) {
    const [accessToken, setAccessToken] = useState(localStorage.getItem('accessToken'));
    const [refreshToken, setRefreshToken] = useState(localStorage.getItem('refreshToken'));
    const [user, setUser] = useState(() => {
        const saved = localStorage.getItem('user');
        return saved ? JSON.parse(saved) : null;
    });
    const [encryptPassword, setEncryptPassword] = useState(localStorage.getItem('encryptPassword') || '');

    // Tokens im localStorage ablegen damit man beim Refresh nicht rausfliegt
    useEffect(() => {
        if (accessToken) {
            localStorage.setItem('accessToken', accessToken);
        } else {
            localStorage.removeItem('accessToken');
        }
    }, [accessToken]);

    useEffect(() => {
        if (refreshToken) {
            localStorage.setItem('refreshToken', refreshToken);
        } else {
            localStorage.removeItem('refreshToken');
        }
    }, [refreshToken]);

    useEffect(() => {
        if (user) {
            localStorage.setItem('user', JSON.stringify(user));
        } else {
            localStorage.removeItem('user');
        }
    }, [user]);

    useEffect(() => {
        if (encryptPassword) {
            localStorage.setItem('encryptPassword', encryptPassword);
        } else {
            localStorage.removeItem('encryptPassword');
        }
    }, [encryptPassword]);

    /**
     * Normaler Login mit E-Mail und PW.
     * Checkt ob wir noch 2FA brauchen oder direkt rein dürfen.
     */
    const login = useCallback(async (email, password) => {
        const API_URL = getBaseUrl();
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Anmeldung fehlgeschlagen.');
        }

        // Muss der User noch den Handy-Code eingeben?
        if (data.requires2FA) {
            return { requires2FA: true, tempToken: data.tempToken };
        }

        // Alles gut, direkt einloggen ohne 2FA
        setAccessToken(data.accessToken);
        setRefreshToken(data.refreshToken);
        setUser({ email: data.email || email, role: data.role, userId: data.userId });
        setEncryptPassword(password);
        return { requires2FA: false };
    }, []);

    /**
     * Checkt den 6-stelligen Code aus der App
     */
    const verify2FA = useCallback(async (tempToken, code, password) => {
        const API_URL = getBaseUrl();
        const response = await fetch(`${API_URL}/auth/2fa/verify`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ tempToken, code })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || '2FA-Verifizierung fehlgeschlagen.');
        }

        setAccessToken(data.accessToken);
        setRefreshToken(data.refreshToken);
        setUser({ email: data.email, role: data.role, userId: data.userId });
        setEncryptPassword(password);
    }, []);

    /**
     * Google Login: Tokens direkt vom Callback abgreifen
     */
    const loginWithOAuth = useCallback((accessTokenVal, refreshTokenVal, role, email) => {
        setAccessToken(accessTokenVal);
        setRefreshToken(refreshTokenVal);
        setUser({ email, role, userId: null });
        // Da Google Login, haben wir hier kein klares Passwort
        setEncryptPassword('');
    }, []);

    /**
     * Logout: Alles rauswerfen
     */
    const logout = useCallback(() => {
        setAccessToken(null);
        setRefreshToken(null);
        setUser(null);
        setEncryptPassword('');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
        localStorage.removeItem('encryptPassword');
    }, []);

    /**
     * Holt ein frisches Access Token wenn das alte abgelaufen ist
     */
    const refreshAccessToken = useCallback(async () => {
        const API_URL = getBaseUrl();
        const currentRefreshToken = localStorage.getItem('refreshToken');
        if (!currentRefreshToken) {
            throw new Error('Kein Refresh Token vorhanden.');
        }

        const response = await fetch(`${API_URL}/auth/refresh`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ refreshToken: currentRefreshToken })
        });

        const data = await response.json();

        if (!response.ok) {
            logout();
            throw new Error(data.message || 'Token-Erneuerung fehlgeschlagen.');
        }

        setAccessToken(data.accessToken);
        if (data.refreshToken) {
            setRefreshToken(data.refreshToken);
        }
        return data.accessToken;
    }, [logout]);

    /**
     * Baut den Auth-Header für Fetch-Requests zusammen
     */
    const getAuthHeader = useCallback(() => {
        return accessToken ? { Authorization: `Bearer ${accessToken}` } : {};
    }, [accessToken]);

    /**
     * Fetch-Wrapper: Macht automatisch den Auth-Header dran
     * und wenn 401 kommt (Token abgelaufen), holt es sneaky ein neues und versucht es nochmal.
     */
    const authFetch = useCallback(async (url, options = {}) => {
        const headers = {
            ...options.headers,
            ...getAuthHeader()
        };

        let response = await fetch(url, { ...options, headers });

        // Bei 401: Token erneuern und nochmal versuchen
        if (response.status === 401) {
            try {
                const newToken = await refreshAccessToken();
                const retryHeaders = {
                    ...options.headers,
                    Authorization: `Bearer ${newToken}`
                };
                response = await fetch(url, { ...options, headers: retryHeaders });
            } catch (refreshError) {
                // Refresh fehlgeschlagen, Benutzer ausloggen
                logout();
                throw new Error('Sitzung abgelaufen. Bitte erneut anmelden.');
            }
        }

        return response;
    }, [getAuthHeader, refreshAccessToken, logout]);

    const isAuthenticated = !!accessToken && !!user;
    const isAdmin = user?.role === 'ROLE_ADMIN';

    const value = {
        accessToken,
        refreshToken,
        user,
        encryptPassword,
        isAuthenticated,
        isAdmin,
        login,
        verify2FA,
        loginWithOAuth,
        logout,
        refreshAccessToken,
        getAuthHeader,
        authFetch
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}
