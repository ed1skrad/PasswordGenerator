import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PasswordGenerator from './components/PasswordGenerator/PasswordGenerator';
import LoginForm from './components/LogicForm/LoginForm';
import RegistrationForm from './components/RegistrationForm/RegistrationForm';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/register" element={<RegistrationForm />} />
                <Route path="/password" element={<PasswordGenerator />} />
            </Routes>
        </Router>
    );
}

export default App;
