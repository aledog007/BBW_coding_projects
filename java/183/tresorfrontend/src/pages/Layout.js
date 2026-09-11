import { Outlet, Link, useNavigate } from "react-router-dom";
import { useAuth } from '../context/AuthContext';

/**
 * Layout
 * @author Peter Rutschmann
 */
const Layout = ({loginValues}) => {
    const { user, isAuthenticated, isAdmin, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = (e) => {
        e.preventDefault();
        logout();
        navigate('/');
    };
    return (
        <>
            <nav>
                <h1>The secret tresor application</h1>
                <p>{isAuthenticated ? `user: ${user.email}` : 'No user logged in'}</p>
                <ul>
                    <li><span>Secrets</span>
                    <ul>
                        <li><Link to="/secret/secrets">my secrets</Link></li>
                        <li><Link to="/secret/newcredential">new credential</Link></li>
                        <li><Link to="/secret/newcreditcard">new credit-card</Link></li>
                        <li><Link to="/secret/newnote">new note</Link></li>
                    </ul>
                    </li>
                    <li><span>User</span>
                        <ul>
                            <li><Link to="/user/register">register</Link></li>
                            {isAuthenticated ? (
                                <>
                                    <li><Link to="/setup-2fa">2FA einrichten</Link></li>
                                    <li><a href="/" onClick={handleLogout}>logout</a></li>
                                </>
                            ) : (
                                <li><Link to="/user/login">login</Link></li>
                            )}
                        </ul>
                    </li>
                    <li><span>Admin</span>
                        <ul>
                            <li><Link to="/user/users">All users</Link></li>
                            <li>Add user</li>
                            <li><Link to="/user/users/:id">Edit user</Link></li>
                            <li>All secrets</li>
                        </ul>
                    </li>
                    <li>
                        <Link to="/">About</Link>
                    </li>
                </ul>
            </nav>
            <hr/>
            <Outlet/>
        </>
    )
};

export default Layout;