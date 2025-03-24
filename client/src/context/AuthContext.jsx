import React, { createContext, useContext, useState, useEffect } from "react";
import accountApi from "../api/accountApi";
const AuthContext = createContext();
import { useNavigate } from "react-router-dom";  // Import useNavigate


export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isLoading, setLoading] = useState(true);
    const navigate = useNavigate();  // Hook để điều hướng trang


    // ✅ Kiểm tra token khi ứng dụng khởi động
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setIsLoggedIn(true);
        }
        setLoading(false); // Khi kiểm tra xong, dừng loading
    }, []);

    // ✅ Hàm login
    const login = async (loginDto) => {
        try {
            const response = await accountApi.login(loginDto);
            if (response && response.token) {
                localStorage.setItem("token", response.token);
                setIsLoggedIn(true);
                navigate("/");
            } else {
                throw new Error("Invalid login response");
            }
        } catch (error) {
            setIsLoggedIn(true);
            throw error;
        }
    };
    
    const logout = () => {
        localStorage.removeItem("token");
        setIsLoggedIn(false);
    };

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                login,
                setIsLoggedIn,
                logout, // ✅ Thêm hàm logout
            }}
        >
            {isLoading ? <div>Loading...</div> : children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
