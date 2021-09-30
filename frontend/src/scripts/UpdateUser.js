import React from 'react'
import { useLocation } from 'react-router'
import { useHistory } from 'react-router'
import axios from 'axios'

import { RefreshTokenContext } from './ReactRouterSetup'

const UpdateUser = () => {
    const location = useLocation();
    const history = useHistory();
    const [authInfo, setAuthInfo] = React.useState(JSON.parse(localStorage.getItem("authInfo")));
    const {getNewAccessToken} = React.useContext(RefreshTokenContext);
    const [updateInfo, setUpdateInfo] = React.useState({
                                                            firstname:location.state.firstname,
                                                            lastname:location.state.lastname,
                                                            email:location.state.email,
                                                            dob:location.state.dob,
                                                            address:location.state.address
                                                        });
    const [modal, setModal] = React.useState({message:"Please ensure there are no missing fields", isError: false});
    const saveButtonRef = React.useRef();

    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;

        setUpdateInfo({...updateInfo, [name]:value});
    }

    const handleSubmit = async event => {
        event.preventDefault();

        let isEmpty = false;

        for (const property in updateInfo){
            if (updateInfo[property] === ""){
                isEmpty = true;
            }
        }
        if (isEmpty){
            setModal({message:"There are empty fields", isError: true});
        } else {
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${authInfo.accessToken}`
                }
            }
            
            await axios.put("http://localhost:8080/api/user/update", {...updateInfo, "username":authInfo.username}, config)
                    .then(res => setModal({message: "Particulars updated successfully!", isError: false}))
                    .catch(error => {
                            if(String(error.response.data.error_message).includes("expired")){
                                getNewAccessToken(authInfo);
                                setAuthInfo(JSON.parse(localStorage.getItem("authInfo")));
                                saveButtonRef.current.click();
                            }
                        }); 
        }
    }

    return (
        <div className="container update-form">
            <form onSubmit={handleSubmit}>
                <h1>Update Particulars</h1>
                <p className={modal.isError? "error": ""}>{modal.message}</p>             
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
                    name = "address"
                    value = {updateInfo.address}
                    onChange = {handleChange}
                    placeholder = "Address"
                />
                <button type="submit" ref={saveButtonRef}>Save</button>
                <button type="button" onClick={() => history.push("/homepage")}>Cancel</button>
            </form>
        </div>
    )
}

export default UpdateUser
