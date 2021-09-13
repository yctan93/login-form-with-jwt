import React from 'react'
import {useHistory} from 'react-router-dom'
import axios from 'axios'
import Cookies from 'js-cookie'

axios.defaults.withCredentials = true;

const LoginForm = () => {
    const [loginInfo, setLoginInfo] = React.useState({username:"", password:""});
    const history = useHistory();
    
    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;

        setLoginInfo({...loginInfo, [name]:value});
    }

    const handleSubmit = async event => {
        event.preventDefault();
        let isEmpty = false;

        for (let property in loginInfo){
            if(loginInfo[property] === ""){
                isEmpty = true;
            }
        }

        if (!isEmpty){
            console.log("Sign in");
            const response = await axios.post("http://localhost:8080/api/login", loginInfo);
            console.log(response);
            console.log("CSRF token: ", Cookies.get("XSRF-TOKEN"));
            console.log("Access token: ", response.data.access_token);
            console.log("Refresh token: ", response.data.refresh_token);

        } else{
            console.log("Empty fields");
        }
        setLoginInfo({username:"", password:""});
    }
    
    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input type="text"
                       name="username"
                       value={loginInfo.username}
                       onChange={handleChange}
                       placeholder="Username"
                />
                <input type="password"
                       name="password"
                       value={loginInfo.password}
                       onChange={handleChange}
                       placeholder="Password"
                />
                <button type="submit">Sign in</button>
                <button type="button" onClick={() => history.push("/signup")}>Sign up</button>
            </form>
        </div>
    )
}

export default LoginForm
