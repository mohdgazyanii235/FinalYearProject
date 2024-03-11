import React, { useEffect, useState } from 'react';
import './App.css';
import Login from './Login';
import { SERVER_URL } from "./constant";
import UserInfo from "./UserInfo";
import OnboardingChoice from "./OnboardingChoice";
import CompleteOnboarding from "./CompleteOnboarding";
import UserDashboard from "./UserDashboard";
import './Dashboard.css';
import Logout from "./Logout";

function App() {
    const [email, setEmail] = useState(null);
    const [roles, setRoles] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [cookieExists, setCookieExists] = useState(false);

    useEffect(() => {
        const getEmailFromCookie = () => {
            const cookieName = 'inf';
            const cookies = document.cookie.split('; ');
            const cookiePair = cookies.find(cookie => cookie.startsWith(cookieName + '='));
            if (cookiePair) {
                setCookieExists(true);
                const encodedValue = cookiePair.split('=')[1];
                const decodedValue = atob(encodedValue);
                try {
                    return JSON.parse(decodedValue).email;
                } catch (e) {
                    console.error('Error parsing email from cookie', e);
                    return null;
                }
            }
            return null;
        };

        setEmail(getEmailFromCookie());
    }, []); // Empty dependency array means this effect runs once on mount

    useEffect(() => {
        if (email) {
            setIsLoading(true);
            fetch(`${SERVER_URL}/user/get/${encodeURIComponent(email)}/roles`, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
            })
                .then(response => {
                    if (!response.ok) throw new Error('Failed to fetch roles');
                    return response.json();
                })
                .then(data => setRoles(data))
                .catch(error => {
                    console.error('Error fetching roles:', error);
                    setError(error);
                })
                .finally(() => setIsLoading(false));
        }
    }, [email]); // This effect runs when `email` changes

    function roleCheck(roleName) {
        if (roles == null) {
            return null;
        }
        return roles.some(role => role === roleName);
    }

    console.log(email);
    console.log(roles);



    return (
        <div>
            <button className="logout-button">logout</button>
            {isLoading ? (
                <div className="App">
                    <p>Loading...</p>
                </div>
            ) : roleCheck("ROLE_NON_ONBOARDED_USER_A") ? (
                <div className="App">
                    <UserInfo email={email}/>
                </div>
            ) : roleCheck("ROLE_NON_ONBOARDED_USER_B") ? (
                <div className="App">
                    <OnboardingChoice email={email}/>
                </div>
            ) : roleCheck("ROLE_NON_ONBOARDED_USER_C") ? (
                <div className="App">
                    <CompleteOnboarding email={email}/>
                </div>
            ) : roleCheck("ROLE_USER") ? (
                <UserDashboard email={email}/>
            ) : <div className="App">
                    <Login />
                </div>
            }
        </div>

    );
}

export default App;
