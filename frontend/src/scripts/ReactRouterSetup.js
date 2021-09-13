import React from 'react'
import { BrowserRouter, BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import { CookiesProvider } from 'react-cookie'

// Pages
import LoginForm from './LoginForm'
import SignUpForm from './SignUpForm'

const ReactRouterSetup = () => {
    return (
        <CookiesProvider>
            <Router>
                <Switch>
                    <Route exact path = "/"><LoginForm /></Route>
                    <Route exact path = "/signup"><SignUpForm /></Route>
                </Switch>
            </Router>
        </CookiesProvider>
    );
}

export default ReactRouterSetup
