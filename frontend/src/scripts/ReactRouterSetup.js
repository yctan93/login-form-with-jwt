import React from 'react'
import { useHistory, BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import { CookiesProvider } from 'react-cookie'
import axios from 'axios'

// Pages
import LoginForm from './LoginForm'
import SignUpForm from './SignUpForm'
import Homepage from './Homepage'
import UpdateUser from './UpdateUser'
import PasswordChange from './PasswordChange'

export const RefreshTokenContext = React.createContext();

const ReactRouterSetup = () => {
    const history = useHistory();

    const getNewAccessToken = async (auth) => {
        const config = {
                            headers: {
                                "Content-Type": "application/json",
                                "Authorization": `Bearer ${auth.refreshToken}`
                            }
                        }
        await axios.post("http://localhost:8080/api/token/refresh", {"username":auth.username}, config)
                    .then(res => {
                        localStorage.clear();
                        localStorage.setItem("authInfo", JSON.stringify({username:auth.username, accessToken: res.data.access_token, refreshToken: res.data.refresh_token}));
                    })
                    .catch(error => {
                        localStorage.clear();
                        history.push("/");
                    });    
    }

    return (
        <RefreshTokenContext.Provider value={{getNewAccessToken}}>
            <CookiesProvider>
                <Router>
                    <Switch>
                            <Route exact path = "/"><LoginForm /></Route>
                            <Route exact path = "/signup"><SignUpForm /></Route>
                            <Route exact path = "/homepage"><Homepage /></Route>
                            <Route exact path = "/update"><UpdateUser /></Route>
                            <Route exact path = "/passwordChange"><PasswordChange /></Route>
                    </Switch>
                </Router>
            </CookiesProvider>
        </RefreshTokenContext.Provider>
    );
}

export default ReactRouterSetup
