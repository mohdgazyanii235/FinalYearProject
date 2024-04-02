import React from "react";
import {SERVER_URL} from "./constant";
import { getCookie } from "./cookieUtils";

function CompleteOnboarding({ email }) {

    const completeOnboardingURL = `${SERVER_URL}/onboarding/${email}/onboardingComplete`;
    const payload = {email};

    const completeOnboarding = async (e) => {
        const response = fetch(completeOnboardingURL, {
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
    }

    return (
        <div className="onboarding-container">
            <button onClick={completeOnboarding}>Complete Onboarding</button>
        </div>
    )
}

export default CompleteOnboarding;