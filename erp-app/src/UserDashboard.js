import React, {useEffect, useState} from "react";
import {SERVER_URL} from "./constant";
import Logout from "./Logout";

function UserDashboard({ email }) {

    const [userDetails, setUserDetails] = useState({ roles: []});
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
            .then(data => {
                setUserDetails(data);
            })
            .catch(error => console.error("Error: Can't fetch user details"));
    }, []);

    console.log(userDetails);
    function roleCheck(roleName) {
        userDetails.roles.forEach(item => {
            if(item.authority === roleName) {
                return true;
            }
        });
        return false;
    }

    function NavBar() {

        return (
            <nav className="navbar navbar-expand-lg">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">{userDetails.company.companyName}</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <a className="nav-link active" aria-current="page" href="#">Home</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">My Documents</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">Holiday Request</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">Employment Details</a>
                            </li>
                            {roleCheck("ROLE_ADMIN") && (
                                <li className="nav-item">
                                    <a className="nav-link" href="#">Role Manager</a>
                                </li>
                            )}

                            <li className="nav-item">
                                <a className="nav-link" onClick={Logout}>Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }


    return (
        <div className="Dashboard">
            <NavBar/>
        </div>
    );

}

export default UserDashboard;