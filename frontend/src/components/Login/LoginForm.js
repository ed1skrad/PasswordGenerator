import React, { useState } from 'react';
import axios from 'axios';
import '../../css/LoginForm/loginForm.css';
import API_URL from "../../config/config";

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`${API_URL}/api/auth/signin`, {
                username,
                password,
            });
            localStorage.setItem('token', response.data.token);
            window.location.href = '/password';
        } catch (error) {
            console.error('Failed to login: ', error);
        }
    };

    return (
        <div>
            <form className="login-form" onSubmit={handleSubmit}>
                <label>
                    <p>Username:</p>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </label>
                <br/>
                <label>
                    <p>Password:</p>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <br/>
                <button className={"login-button"} type="submit">Login</button>
            </form>
        </div>
    );
};

export default LoginForm;
