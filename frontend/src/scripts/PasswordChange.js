import React from 'react'
import {useHistory} from 'react-router-dom'
import axios from 'axios'
import { RefreshTokenContext } from './ReactRouterSetup'

const PasswordChange = () => {
    const [authInfo, setAuthInfo] = React.useState(JSON.parse(localStorage.getItem("authInfo")));
    const [PasswordChange, setPasswordChange] = React.useState({password:"" });
    const [confirmPassword, setConfirmPassword] = React.useState("");
    const [modal, setModal] = React.useState({message:"Please ensure that no fields are empty", isError: false});
    const history = useHistory();
    const {getNewAccessToken} = React.useContext(RefreshTokenContext);
    const updateButtonRef = React.useRef();

    const handleChange = event => {
        console.log(event.target.value)
        const name = event.target.name;
        const value = event.target.value;

        if (name !== "password_confirmation") {
            setPasswordChange({...PasswordChange, [name]:value});
        }
        else {
            setConfirmPassword(value);
        }

    }

    const handleSubmit = async event => {
        event.preventDefault();

        let isEmpty = false;

        for (const property in PasswordChange){
            if (PasswordChange[property] === ""){
                isEmpty = true;
            }
        }

        if (isEmpty){
            setModal({message:"There are empty fields", isError: true});
        } else {
            if (PasswordChange.password !== confirmPassword){
                setModal({message:"Password is not the same!", isError: true});
            } else {
                const config = {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${authInfo.accessToken}`
                    }
                }
                await axios.put("http://localhost:8080/api/user/update", {...PasswordChange, "username":authInfo.username}, config)
                           .then(res => {
                                    setConfirmPassword("");
                                    setPasswordChange({password:"" });
                                    setModal({message: "Password changed successfully!", isError:false})  
                                })
                            .catch(error => {
                                    console.log(error.response);
                                    if(String(error.response.data.error_message).includes("expired")){
                                        getNewAccessToken(authInfo);
                                        setAuthInfo(JSON.parse(localStorage.getItem("authInfo")));
                                        updateButtonRef.current.click();
                                    } else if(String(error.response.data.error_message).includes("ERROR_SAME_PASSWORD")){
                                        setModal({message: "Please use a different password!", isError:true});
                                    }
                                })
                
            }
        }
    }
    
    return (
        <div className="container password-change">
            <form onSubmit={handleSubmit}>
                <h1>Change Password</h1>
                <p className={modal.isError? "error" : ""}>{modal.message}</p>
                <input 
                    type = "password"
                    name = "password"
                    value = {PasswordChange.password}
                    onChange = {handleChange}
                    placeholder = "New Password"
                />
                <input 
                    type = "password"
                    name = "password_confirmation"
                    value = {confirmPassword}
                    onChange = {handleChange}
                    placeholder = "Confirm Password"
                />
                <button type="submit" ref={updateButtonRef}>Save</button>
                <button type="button" onClick={() => history.push("/homepage")}>Cancel</button>
            </form>
        </div>
    )
}

export default PasswordChange
