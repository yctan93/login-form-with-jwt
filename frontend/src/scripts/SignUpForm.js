import React from 'react'

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

    const handleSubmit = event => {
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
                console.log('Sign up!');
                setSignupInfo({
                                username:"",
                                password:"",
                                firstname:"",
                                lastname:"",
                                email:"",
                                dob:""
                              });
                setConfirmPassword("");
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
                    placeholder = "DD-MM-YYYY"
                />
                <button type="submit">Sign Up</button>
                <button type="button">Cancel</button>
            </form>
        </div>
    )
}

export default SignUpForm
