import React, {useEffect, useState} from "react";
import {SERVER_URL} from "./constant";

function UserDashboard({ email }) {

    const [userDetails, setUserDetails] = useState(null);
    const userDetailsURL = `${SERVER_URL}/user/get/${email}/userDetails`;

    useEffect(() => {
        fetch(userDetailsURL, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => setUserDetails(data))
            .catch(error => console.error("Error: Can't fetch user details"));
    }, []);

    console.log(userDetails);

    return (
        <div className="onboarding-container">
            <h1>Hello There</h1>
        </div>
    )

}

export default UserDashboard;