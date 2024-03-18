import React from "react";
import {SERVER_URL} from "./constant";

function Login() {
    const handleLogin = (provider) => {
        window.location.href = SERVER_URL + `/oauth2/authorization/${provider}`;
    };

    return (
        <div className="button-container">
            <button className="login-button" onClick={() => { handleLogin('google') }}>Login with Google</button>
            <button className="login-button" onClick={() => { handleLogin('auth-server'); }}>Login with Auth-Server</button>
        </div>
    );
}

export default Login;