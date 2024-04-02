import React, {useEffect, useState} from "react";
import {SERVER_URL} from "./constant";
import { getCookie } from "./cookieUtils";

function JoinCompany({ email }) {

    const joinCompanyURL = `${SERVER_URL}/onboarding/${email}/joinCompany`;
    const companiesListURL = `${SERVER_URL}/company/get/all`;
    const [companyName, setCompanyName] = useState('');
    const [companiesList, setCompaniesList] = useState([]);
    const payload = { email, companyName }


    useEffect(() => {
        fetch(companiesListURL, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": getCookie('XSRF-TOKEN'),
            },
        })
            .then(response => response.json())
            .then(data => setCompaniesList(data))
            .catch(error => console.error("Error: Can't fetch companies list"));
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const response = await fetch(joinCompanyURL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: 'include',
            body: JSON.stringify(payload),
        })

        if (response.ok) {
            window.location.href = window.location;
        }
    }

    return (
        <div className="onboarding-container">
            <form onSubmit={handleSubmit}>
                <label htmlFor="companyName">Select Company: </label>
                <select
                    id="companyName"
                    value={companyName}
                    onChange={(e) => setCompanyName(e.target.value)}
                >
                    <option value="">--Please choose an option--</option>
                    {companiesList.map((company, index) => (
                        <option key={index} value={company}>{company}</option>
                    ))}
                </select>
                <button type="submit">Join Company</button>
            </form>
        </div>
    )
}

export default JoinCompany;