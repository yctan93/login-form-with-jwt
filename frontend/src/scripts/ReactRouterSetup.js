import React from 'react'
import { BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import { CookiesProvider } from 'react-cookie'

// Pages
import LoginForm from './LoginForm'
import SignUpForm from './SignUpForm'
import Homepage from './Homepage'
import UpdateUser from './UpdateUser'

const ReactRouterSetup = () => {
    

    return (
        <CookiesProvider>
            <Router>
                <Switch>
                        <Route exact path = "/"><LoginForm /></Route>
                        <Route exact path = "/signup"><SignUpForm /></Route>
                        <Route exact path = "/homepage"><Homepage /></Route>
                        <Route exact path = "/update"><UpdateUser /></Route>
                </Switch>
            </Router>
        </CookiesProvider>
    );
}

export default ReactRouterSetup
