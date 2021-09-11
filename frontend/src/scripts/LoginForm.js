import React from 'react'

const LoginForm = () => {
    const [loginInfo, setLoginInfo] = React.useState({username:"", password:""});

    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;

        setLoginInfo({...loginInfo, [name]:value});
    }

    const handleSubmit = event => {
        event.preventDefault();
        let isEmpty = false;

        for (let property in loginInfo){
            if(loginInfo[property] === ""){
                isEmpty = true;
            }
        }

        if (!isEmpty){
            console.log("Sign in");
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
            </form>
        </div>
    )
}

export default LoginForm
