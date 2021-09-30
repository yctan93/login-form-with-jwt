import React from 'react'
import { useHistory } from 'react-router-dom'
import axios from 'axios'

axios.defaults.withCredentials = true;
axios.defaults.xsrfHeaderName = "X-XSRF-TOKEN";

const LoginForm = () => {
    const [loginInfo, setLoginInfo] = React.useState({username:"", password:""});
    const [modal, setModal] = React.useState({message:"Please Sign In", isError: false});
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
            await axios.post("http://localhost:8080/api/login", loginInfo)
                       .then(res => {
                            const authInfo = {username:loginInfo.username, accessToken: res.data.access_token, refreshToken: res.data.refresh_token};
                            localStorage.setItem("authInfo", JSON.stringify(authInfo));
                            setLoginInfo({username:"", password:""});
                            history.push("/homepage");
                       })
                       .catch(error => {
                           setModal({message:"Invalid username/password", isError:true});
                           setLoginInfo({username:"", password:""});
                       });
        } else{
            console.log("Empty fields");
            setLoginInfo({username:"", password:""});
        }
        
    }
    
    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <h1>Welcome!</h1>
                <p className={modal.isError ? "error": ""}>{modal.message}</p>
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
                <button type="submit" className="btn">Sign in</button>
                <button type="button" className="btn" onClick={() => history.push("/signup")}>Create New Account</button>
            </form>
        </div>
    )
}

export default LoginForm
