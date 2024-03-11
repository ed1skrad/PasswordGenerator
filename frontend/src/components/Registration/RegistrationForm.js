import React, { useState } from 'react';
import axios from 'axios';
import '../../css/RegistrationForm/registrationForm.css';

const RegistrationForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/signup', {
                username,
                email,
                password,
            });
            localStorage.setItem('token', response.data.token);
            window.location.href = '/password'; // Перенаправление на страницу PasswordGenerator
        } catch (error) {
            console.error('Failed to register: ', error);
        }
    };

    return (
        <div>
            <form className="registration-form" onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                </label>
                <br />
                <label>
                    Email:
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                </label>
                <br />
                <label>
                    Password:
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </label>
                <br />
                <button type="submit">Register</button>
            </form>
        </div>
    );
};

export default RegistrationForm;
