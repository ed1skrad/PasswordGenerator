import React, { useState } from 'react';
import axios from 'axios';
import '../../css/AdminPanel/adminPanel.css';
import API_URL from "../../config/config";

const AdminPanel = () => {
    const [list, setList] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.get(`${API_URL}/api/password/all`);
            localStorage.setItem('token', response.data.token);
            window.location.href = '/password';

            const passwords = response.data.map(item => ({
                passwordId: item.id,
                password: item.password,
                username: item.user.username
            }));

            setList(passwords);
        } catch (error) {
            console.error('Failed to login: ', error);
        }
    };

    return (
        <div>
            {list.map(item => (
                <div key={item.passwordId}>
                    <h3>Имя пользователя: {item.username}</h3>
                    <p>ID пароля: {item.passwordId}</p>
                    <p>Пароль: {item.password}</p>
                </div>
            ))}
        </div>
    );
};

export default AdminPanel;
