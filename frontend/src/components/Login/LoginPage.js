import React from 'react';
import LoginForm from './LoginForm';

const LoginPage = () => {
    return (
        <div className={"login-body"}>
            <LoginForm />
            <p>
                Don't have an account? <a className={"signUp"} href="/register">Sign up</a>
            </p>
        </div>
    );
};

export default LoginPage;
