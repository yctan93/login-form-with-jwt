import React from 'react'
import { useLocation } from 'react-router'
import { useHistory } from 'react-router'
import axios from 'axios'

const UpdateUser = () => {
    const location = useLocation();
    const history = useHistory();
    const [updateInfo, setUpdateInfo] = React.useState({
                                                            firstname:location.state.firstname,
                                                            lastname:location.state.lastname,
                                                            email:location.state.email,
                                                            dob:location.state.dob,
                                                            address:location.state.address
                                                        });
    const AuthInfo = JSON.parse(localStorage.getItem("authInfo"));
    
    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;

        setUpdateInfo({...updateInfo, [name]:value});
    }

    const handleSubmit = async event => {
        event.preventDefault();
        const config = {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${AuthInfo.accessToken}`
            }
        }
        const response = await axios.put("http://localhost:8080/api/user/update", {...updateInfo, "username":AuthInfo.username}, config);
        console.log(response)
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>              
                <input 
                    type = "text"
                    name = "firstname"
                    value = {updateInfo.firstname}
                    onChange = {handleChange}
                    placeholder = "Firstname"
                />
                <input 
                    type = "text"
                    name = "lastname"
                    value = {updateInfo.lastname}
                    onChange = {handleChange}
                    placeholder = "lastname"
                />
                <input 
                    type = "email"
                    name = "email"
                    value = {updateInfo.email}
                    onChange = {handleChange}
                    placeholder = "Email"
                />
                <input 
                    type = "text"
                    name = "dob"
                    value = {updateInfo.dob}
                    onChange = {handleChange}
                    placeholder = "YYYY-MM-DD"
                />
                <textarea 
                    type = "text"
                    name = "dob"
                    value = {updateInfo.address}
                    onChange = {handleChange}
                    placeholder = "Address"
                />
                <button type="submit">Save</button>
                <button type="button" onClick={() => history.push("/homepage")}>Cancel</button>
            </form>
        </div>
    )
}

export default UpdateUser