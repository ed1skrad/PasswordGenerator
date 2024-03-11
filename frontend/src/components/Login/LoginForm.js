import React, { useState } from 'react';
import axios from 'axios';
import '../../css/LoginForm/loginForm.css';

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/signin', {
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
                    Username:
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </label>
                <br/>
                <label>
                    Password:
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <br/>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default LoginForm;
