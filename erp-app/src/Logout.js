import {SERVER_URL} from "./constant";

function Logout() {
    const logoutUrl = `${SERVER_URL}/logout`
    const response = fetch(logoutUrl, {
        method: "GET",
        credentials: "include",
    }).then(response => {
        console.log("logged out")
        localStorage.removeItem("inf");
        localStorage.removeItem("JSESSIONID");
        window.location.href = window.location; // neat way to refresh
    });
}

export default Logout;