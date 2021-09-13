import React from 'react'
import {useHistory} from 'react-router-dom'
import axios from 'axios'

const SignUpForm = () => {
    const [signupInfo, setSignupInfo] = React.useState({
                                                            username:"",
                                                            password:"",
                                                            firstname:"",
                                                            lastname:"",
                                                            email:"",
                                                            dob:""
                                                        });
    const [confirmPassword, setConfirmPassword] = React.useState("");
    const history = useHistory();

    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;
        
        if (name !== "password_confirmation") {
            setSignupInfo({...signupInfo, [name]:value});
        }
        else {
            setConfirmPassword(value);
        }
        if (signupInfo.password !== confirmPassword){
            console.log("Password is not the same!")
        }
    }

    const handleSubmit = async event => {
        event.preventDefault();

        let isEmpty = false;

        for (const property in signupInfo){
            if (signupInfo[property] === ""){
                isEmpty = true;
            }
        }

        if (isEmpty){
            console.log('Empty fields!');
        } else {
            if (signupInfo.password !== confirmPassword){
                console.log("Password is not the same!");
            } else {
                const response = await axios.post("http://localhost:8080/api/signup", signupInfo);
                console.log(response);
                setConfirmPassword("");
                setSignupInfo({username:"", password:"", firstname:"", lastname:"", email:"", dob:""});
                
            }
        }   
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input 
                    type = "text"
                    name = "username"
                    value = {signupInfo.username}
                    onChange = {handleChange}
                    placeholder = "Username"
                />
                <input 
                    type = "password"
                    name = "password"
                    value = {signupInfo.password}
                    onChange = {handleChange}
                    placeholder = "Password"
                />
                <input 
                    type = "password"
                    name = "password_confirmation"
                    value = {confirmPassword}
                    onChange = {handleChange}
                    placeholder = "Confirm Password"
                />
                <input 
                    type = "text"
                    name = "firstname"
                    value = {signupInfo.firstname}
                    onChange = {handleChange}
                    placeholder = "Firstname"
                />
                <input 
                    type = "text"
                    name = "lastname"
                    value = {signupInfo.lastname}
                    onChange = {handleChange}
                    placeholder = "lastname"
                />
                <input 
                    type = "email"
                    name = "email"
                    value = {signupInfo.email}
                    onChange = {handleChange}
                    placeholder = "Email"
                />
                <input 
                    type = "text"
                    name = "dob"
                    value = {signupInfo.dob}
                    onChange = {handleChange}
                    placeholder = "YYYY-MM-DD"
                />
                <button type="submit">Sign Up</button>
                <button type="button" onClick={() => history.push("/")}>Cancel</button>
            </form>
        </div>
    )
}

export default SignUpForm
