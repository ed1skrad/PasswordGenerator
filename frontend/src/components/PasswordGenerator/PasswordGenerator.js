import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import '../../css/PasswordGenerator/passwordGenerator.css';
import StarBackground from "../Background/StarBackground";
import API_URL from "../../config/config";

const PasswordGenerator = () => {
    const [difficulty, setDifficulty] = useState('EASY');
    const [length, setLength] = useState(8);
    const [password, setPassword] = useState('');
    const buttonRef = useRef(null);
    const [animationPlaying, setAnimationPlaying] = useState(false);
    const [showAlert, setShowAlert] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        setAnimationPlaying(false);
        buttonRef.current.style.animationPlayState = 'paused';

        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            };

            const response = await axios.post(`${API_URL}/api/password/generatePassword`, {
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

    const copyPasswordToClipboard = (event) => {
        navigator.clipboard.writeText(password);
        event.preventDefault();
        setShowAlert(true);
        setTimeout(() => {
            setShowAlert(false);
        }, 2000)
    };

    const closeAlert = () => {
        setShowAlert(false);
    };

    useEffect(() => {
        if (!animationPlaying) {
            setTimeout(() => {
                buttonRef.current.style.animationPlayState = 'running';
                setAnimationPlaying(true);
            }, 30000);
        }
    }, [animationPlaying]);

    useEffect(() => {
        setTimeout(() => {
            setAnimationPlaying(true);
        }, 30000);
    }, []);

    return (
        <div className="password-generator">
            <StarBackground />
            <form onSubmit={handleSubmit}>
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
                    <input type="number" value={length} onChange={handleLengthChange}/>
                </label>
                <br/>
                <button type="submit" className={"generate-button"} ref={buttonRef}>Generate Password</button>
            </form>
            {showAlert && (
                <div className={"alert"}>
                    <p>Copied to clipboard!</p>
                </div>
            )}
            {password && (
                <div className="password-generator-button" onClick={copyPasswordToClipboard}>
                    {password}
                </div>
            )}
        </div>
    );
};

export default PasswordGenerator;
