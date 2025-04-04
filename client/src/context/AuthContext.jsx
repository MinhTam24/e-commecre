import React, { createContext, useContext, useState, useEffect } from "react";
import accountApi from "../api/accountApi";
const AuthContext = createContext();
import { useNavigate } from "react-router-dom";  // Import useNavigate
import { jwtDecode } from "jwt-decode";


export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isLoading, setLoading] = useState(true);
    const [userId, setUserId] = useState("");
    const navigate = useNavigate();  // Hook để điều hướng trang


    // Kiểm tra token khi ứng dụng khởi động
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setIsLoggedIn(true);
            try {
                const decoded = jwtDecode(token);
                setUserId(decoded.userId); // Lưu ID từ token
                setIsLoggedIn(true);
            } catch (error) {
                console.error("Invalid token", error);
                localStorage.removeItem("token");
                setIsLoggedIn(false);
            }
        }
        setLoading(false); // Khi kiểm tra xong, dừng loading
    }, []);

    // Hàm login
    const login = async (loginDto) => {
        try {
            const response = await accountApi.login(loginDto);
            if (response && response.token) {
                localStorage.setItem("token", response.token);
                setIsLoggedIn(true);
                navigate("/");
                setTimeout(() => {
                    window.location.reload();
                }, 100);
            } else {
                throw new Error("Invalid login response");
            }
        } catch (error) {
            setIsLoggedIn(false);
            throw error;
        }
    };
    
    const logout = () => {
        localStorage.removeItem("token");
        setUserId("");
        setIsLoggedIn(false);
    };

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                login,
                userId,
                setIsLoggedIn,
                logout, // ✅ Thêm hàm logout
            }}
        >
            {isLoading ? <div>Loading...</div> : children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
