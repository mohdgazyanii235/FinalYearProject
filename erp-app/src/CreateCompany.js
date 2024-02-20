import React, {useEffect, useState} from "react";
import {SERVER_URL} from "./constant";

function CreateCompany({ email }) {

    const createCompanyURL = `${SERVER_URL}/onboarding/${email}/createCompany`;
    const [name, setCompanyName] = useState('');
    const [address, setCompanyAddress] = useState('');
    const payload = { email, name, address }


    const handleSubmit = async (e) => {
        e.preventDefault();

        const response = await fetch(createCompanyURL, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload),
        });
        if (response.ok) {
            window.location.href = window.location;
        }
    }

    return (
        <div className="onboarding-container">
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Company Name: </label>
                    <input
                        type="text"
                        id="companyName"
                        value={name}
                        onChange={(e) => setCompanyName(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="address">Company Address: </label>
                    <input
                        type="text"
                        id="companyAddress"
                        value={address}
                        onChange={(e) => setCompanyAddress(e.target.value)}
                    />
                </div>
                <button type="submit">Create Company</button>
            </form>
        </div>
    )
}

export default CreateCompany