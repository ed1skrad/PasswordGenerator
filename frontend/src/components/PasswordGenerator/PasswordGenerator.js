import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../css/PasswordGenerator/passwordGenerator.css';

const PasswordGenerator = () => {
    const [difficulty, setDifficulty] = useState('EASY');
    const [length, setLength] = useState(8);
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            };

            const response = await axios.post('http://localhost:8080/api/password/generatePassword', {
                difficulty,
                length,
            }, config);

            setPassword(response.data);
        } catch (error) {
            console.error('Failed to generate password: ', error);
        }
    };

    const handleLengthChange = (e) => {
        const value = parseInt(e.target.value);
        if (value >= 0 && value <= 255) {
            setLength(value);
        }
    };

    useEffect(() => {
        const button = document.querySelector('.password-generator button');
        setTimeout(() => {
            button.style.animationPlayState = 'running';
        }, 30000);
    }, []);

    return (
        <div className="password-generator">
            <form onSubmit={handleSubmit}>
                <label>
                    Difficulty:
                    <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
                        <option value="EASY">EASY</option>
                        <option value="NORMAL">NORMAL</option>
                        <option value="HARD">HARD</option>
                    </select>
                </label>
                <br />
                <label>
                    Length:
                    <input type="number" value={length} onChange={handleLengthChange} />
                </label>
                <br />
                <button type="submit">Generate Password</button>
            </form>
            {password && <div className="generated-password">Generated Password: {password}</div>}
        </div>
    );
};

export default PasswordGenerator;
