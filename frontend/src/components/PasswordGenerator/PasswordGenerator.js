import React, { useState } from 'react';
import axios from 'axios';
import '../../css/PasswordGenerator/passwordGenerator.css';


const PasswordGenerator = () => {
    const [difficulty, setDifficulty] = useState('EASY'); // Default difficulty is EASY
    const [length, setLength] = useState(8); // Default length is 8
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

    return (
        <div>
            <form className="password-generator" onSubmit={handleSubmit}>
                <label>
                    Difficulty:
                    <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
                        <option value="EASY">EASY</option>
                        <option value="NORMAL">NORMAL</option>
                        <option value="HARD">HARD</option>
                    </select>
                </label>
                <br/>
                <label>
                    Length:
                    <input type="number" value={length} onChange={(e) => setLength(e.target.value)}/>
                </label>
                <br/>
                <button type="submit">Generate Password</button>
            </form>
            {password && <p>Generated Password: {password}</p>}
        </div>
    );
};

export default PasswordGenerator;
