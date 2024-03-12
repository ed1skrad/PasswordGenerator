import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './components/Login/LoginPage';
import PasswordGenerator from './components/PasswordGenerator/PasswordGenerator';
import RegistrationForm from "./components/Registration/RegistrationForm";
import AdminPanel from "./components/Admin/Admin";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegistrationForm />} />
                <Route path="/password" element={<PasswordGenerator />} />
                <Route path="*" element={<Navigate to="/login" />} />
                <Route path="/admin" element={<AdminPanel />} />
            </Routes>
        </Router>
    );
}

export default App;
