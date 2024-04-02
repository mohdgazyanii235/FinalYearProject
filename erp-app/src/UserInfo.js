import React, { useState } from 'react';
import {SERVER_URL} from "./constant";
import { getCookie } from './cookieUtils';

function UserInfo({ email }) {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const url = `${SERVER_URL}/onboarding/${email}/update/userInfo`;
    const payload = { firstName, lastName };

    const handleSubmit = async (e) => {
        e.preventDefault();

        console.log(JSON.stringify(payload));

        const response = await fetch(url, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": getCookie('XSRF-TOKEN'),
            },
            body: JSON.stringify(payload),
        });

        if (response.ok) {
            window.location.href = window.location;
        }
    };

    return (
        <div className="onboarding-container">
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="firstName">First name:</label>
                    <input
                        type="text"
                        id="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="lastName">Last name:</label>
                    <input
                        type="text"
                        id="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                </div>
                <button type={"submit"}>Submit</button>
            </form>
        </div>
    );
}

export default UserInfo;
