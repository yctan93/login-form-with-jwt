import React from 'react'
import axios from 'axios'
import { useHistory } from 'react-router-dom'

const Homepage = () => {
    const history = useHistory();
    const AuthInfo = JSON.parse(localStorage.getItem("authInfo"));

    const handleUpdate = async () => {
        const config = {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${AuthInfo.accessToken}`
            }
        }
        const response = await axios.post("http://localhost:8080/api/user", {"username":AuthInfo.username}, config);
        history.push("/update", response.data);
    }

    const handleLogout = () => {
        localStorage.clear();
        history.push("/");
    }

    return (
        <div>
            <h1>Homepage</h1>
            <button type="button" onClick={handleUpdate}>Update User</button>
            <button type="button" onClick={handleLogout}>Logout</button>
        </div>
    )
}

export default Homepage
