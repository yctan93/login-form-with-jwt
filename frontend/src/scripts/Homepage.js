import React from 'react'
import axios from 'axios'
import { useHistory } from 'react-router-dom'
import { RefreshTokenContext } from './ReactRouterSetup'

const Homepage = () => {
    const history = useHistory();
    const [authInfo, setAuthInfo] = React.useState(JSON.parse(localStorage.getItem("authInfo")));
    const {getNewAccessToken} = React.useContext(RefreshTokenContext);
    const updateButtonRef = React.useRef();

    const handleUpdate = async () => {
        const config = {
                            headers: {
                                "Content-Type": "application/json",
                                "Authorization": `Bearer ${authInfo.accessToken}`
                            }
                        }
        await axios.post("http://localhost:8080/api/user", {"username":authInfo.username}, config)
                   .then(res => {
                        if (res.data.address == null){
                            res.data.address = "";
                        }
                        history.push("/update", res.data)
                    })
                   .catch(error => {
                        if(String(error.response.data.error_message).includes("expired")){
                            getNewAccessToken(authInfo);
                            setAuthInfo(JSON.parse(localStorage.getItem("authInfo")));
                            updateButtonRef.current.click();
                        }
                    });   
    }   

    const handleLogout = () => {
        localStorage.clear();
        history.push("/");
    }

    return (
        <div className="dashboard">
            <h1>Dashboard</h1>
            <div>
                <button type="button" className=".btn" ref={updateButtonRef} onClick={handleUpdate}>Update Particulars</button>
                <button type="button" className=".btn" ref={updateButtonRef} onClick={() => history.push("/passwordChange")}>Change New Password</button>
                <button type="button" className=".btn" onClick={handleLogout}>Logout</button>
            </div>
        </div>
    )
}

export default Homepage
