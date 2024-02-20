import React, {useState} from "react";
import JoinCompany from "./JoinCompany";
import CreateCompany from "./CreateCompany";

function OnboardingChoice({ email }) {

    const [isJoinCompany, setIsJoinCompany] = useState(false);
    const [isCreateCompany, setIsCreateCompany] = useState(false);

    function joinCompany() {
        setIsJoinCompany(true);
        setIsCreateCompany(false);
    }

    function createCompany() {
        setIsCreateCompany(true);
        setIsJoinCompany(false);
    }

    if (isJoinCompany) {
        return (
            <JoinCompany email={email}/>
        )
    } else if (isCreateCompany) {
        return (
            <CreateCompany email={email}/>
        )
    } else {
        return (
            <div className="onboarding-container">
                <button onClick={joinCompany}>Join Company</button>
                <button onClick={createCompany}>Create Company</button>
            </div>
        )
    }
}

export default OnboardingChoice;