import React from 'react';
import LoginForm from './LoginForm';
import RegistrationForm from '../Registration/RegistrationForm';

const LoginPage = () => {
    return (
        <div>
            <LoginForm />
            <p>
                Don't have an account? <a href="/register">Sign up</a>
            </p>
        </div>
    );
};

export default LoginPage;
