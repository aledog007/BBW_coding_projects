import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

/**
 * ProtectedRoute - Schützt Routen vor unauthentifiziertem Zugriff.
 * Optional kann eine bestimmte Rolle (z.B. ROLE_ADMIN) verlangt werden.
 */
function ProtectedRoute({ children, requiredRole }) {
    const { isAuthenticated, user } = useAuth();

    if (!isAuthenticated) {
        return <Navigate to="/user/login" replace />;
    }

    if (requiredRole && user?.role !== requiredRole) {
        return <Navigate to="/" replace />;
    }

    return children;
}

export default ProtectedRoute;
