import React, { useState } from 'react';
import axios from 'axios';
import '../../css/AdminPanel/adminPanel.css';
import API_URL from "../../config/config";

const AdminPanel = () => {
    const [passwords, setPasswords] = useState([]);
    const [username, setUsername] = useState('');
    const [displayMode, setDisplayMode] = useState('all');

    const handleGetAllPasswords = () => {
        axios.get(`${API_URL}/api/password/all`)
            .then(response => {
                setPasswords(response.data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    const handleGetUserPasswords = () => {
        axios.get(`${API_URL}/api/password/user/${username}/passwords`)
            .then(response => {
                setPasswords(response.data);
            })
            .catch(error => {
                if (error.response && error.response.status === 404) {
                    setPasswords([]);
                } else {
                    console.error('Error:', error);
                }
            });
    };

    const handleChangeUsername = (event) => {
        setUsername(event.target.value);
    };

    const handleDisplayAllPasswords = () => {
        setDisplayMode('all');
        handleGetAllPasswords();
    };

    const handleDisplayUserPasswords = () => {
        setDisplayMode('user');
        handleGetUserPasswords();
    };

    const handleDisplayAllUsers = () => {
        const token = localStorage.getItem('token');
        axios.get(`${API_URL}/api/users`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                setPasswords(response.data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    const handleDelete = async (id) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.delete(`${API_URL}/api/password/delete/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    const handleEdit = () =>{
        
    }

    const deleteAll = () => {
        const confirmed = window.confirm('Вы уверены, что хотите удалить все?');
        if (confirmed) {
            axios.delete(`${API_URL}/api/password/deleteAllBulkPasswords`)
                .then(response => {
                    console.log('Response', response.data);
                    alert('Успешно удалено')
                })
                .catch(error => {
                    console.error('Error:', error);
                })
        }
    }

    return (
        <div>
            <div>
                <button onClick={handleDisplayAllPasswords} className='button-main'>Display All Passwords</button>
                <button onClick={handleDisplayUserPasswords} className='button-main'>Display User Passwords</button>
                <button onClick={handleDisplayAllUsers} className='button-main'>Display All Users</button>
                {displayMode === 'user' && (
                    <input type="text" value={username} onChange={handleChangeUsername} />
                )}
            </div>
            <div className="list-container">
                {passwords.length === 0 ? (
                    <p>No passwords found.</p>
                ) : (
                    passwords.map(passwordObj => (
                        <div key={passwordObj.id} className='pass-block'>
                            <span>Password: {passwordObj.password}</span>
                            <span>Difficulty: {passwordObj.difficulty}</span>
                            <span>User: {passwordObj.user.username}</span>
                            <span>E-mail: {passwordObj.user.email}</span>
                            <button>Edit</button>
                            <button onClick={() => handleDelete(passwordObj.id)}>Delete</button>
                        </div>
                    ))
                )}
            </div>
            <button onClick={deleteAll} className='button-main'>Delete All</button>
        </div>
    );
};

export default AdminPanel;
