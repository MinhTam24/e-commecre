import "../css/login.css";
import { Link } from "react-router-dom";  // Import Link from react-router-dom
import { useAuth } from "../context/AuthContext";
import React, { useState, useEffect } from "react";

const Login = () => {

    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const { login } = useAuth(); // Sử dụng AuthContext
    const [showPassword, setShowPassword] = useState(false);




    const handleLogin = async (e) => {
        e.preventDefault(); // Ngăn reload trang

        try {
            const loginDto = { userName, password };
            await login(loginDto);

        } catch (error) {
            console.error("Lỗi đăng nhập:", error.response.data.message);
            alert(error.response.data.message)
        }
    };


    return (
        <>
            <div className="d-flex justify-content-center align-items-center  login-page">
                <div className="login-container">
                    <h2 className="text-center mb-4 fw-bold">Đăng Nhập</h2>
                    <form>
                        <div className="mb-3 form-floating">
                            <input className="form-control"
                                id="floatingInput"
                                type="text"
                                value={userName}
                                onChange={(e) => setUserName(e.target.value)}
                                placeholder="Nhập email" required />
                            <label for="floatingInput" className="form-label">Tài khoản</label>
                        </div>
                        <div className="mb-3 password-wrapper form-floating">
                            <input className="form-control" id="floatingPassword"
                                type={showPassword ? "text" : "password"}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Nhập mật khẩu" required />
                            <label for="floatingPassword" className="form-label">Mật khẩu</label>
                            <i
                                className={`fas ${showPassword ? "fa-eye" : "fa-eye-slash"}`}
                                id="togglePassword"
                                onClick={() => setShowPassword(!showPassword)}
                                style={{
                                    position: "absolute",
                                    right: "10px",
                                    top: "50%",
                                    cursor: "pointer"
                                }}
                            />
                        </div>
                        <div className="d-flex justify-content-between mb-3">
                            <a href="forgot-password.html" className="text-decoration-none text-blue text-decoration-underline">Quên mật khẩu?</a>
                            <Link to="/signup" className="text-decoration-none text-primary text-decoration-underline" >Tạo tài khoản</Link>

                        </div>
                        <button type="submit" onClick={handleLogin} className="btn btn-primary w-100">Đăng Nhập</button>
                    </form>
                </div>
            </div>

        </>
    )

}

export default Login;


