import { useState } from "react";
import "../css/signup.css";
import { Link } from "react-router-dom";
import accountApi from "../api/accountApi";

const Signup = () => {
    const [showPassword, setShowPassword] = useState(false);
    const [formData, setFormData] = useState({
        firstName: "",
        fullName: "",
        password: "",
        emailOrPhone: "",
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSignUp = async (e) => {
        e.preventDefault();

        // Biểu thức chính quy cho email và số điện thoại
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        const phoneRegex = /^\d{10}$/; // Giả sử chỉ chấp nhận số điện thoại 10 chữ số

        // Kiểm tra xem giá trị nhập vào là email hoặc số điện thoại hợp lệ
        if (!emailRegex.test(formData.emailOrPhone) && !phoneRegex.test(formData.emailOrPhone)) {
            alert("Vui lòng nhập email hoặc số điện thoại hợp lệ.");
            return;
        }

        // Thực hiện đăng ký
        try {
            const response = await accountApi.singUp(formData);
            console.log("Đăng ký thành công!", response);
        } catch (error) {
            console.error("Lỗi khi đăng ký:", error);
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center signup-page">
            <div className="register-container">
                <h2 className="text-center mb-3 fw-bold">Đăng Ký</h2>
                <form onSubmit={handleSignUp}>
                    <div className="mb-2 form-floating">
                        <input
                            id="floatingEmailOrPhone"
                            type="text"
                            className="form-control"
                            name="emailOrPhone"
                            placeholder="Nhập số điện thoại hoặc email"
                            value={formData.emailOrPhone}
                            onChange={handleChange}
                            required
                        />
                        <label htmlFor="floatingEmailOrPhone" className="form-label">SĐT hoặc Email</label>
                    </div>
                    <div className="row form-floating mb-2">
                        <div className="col-md-6">
                            <div className="form-floating">
                                <input
                                    id="floatingName"
                                    type="text"
                                    className="form-control"
                                    name="firstName"
                                    placeholder=" "
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    required
                                />
                                <label htmlFor="floatingName">Tên</label>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="form-floating">
                                <input
                                    id="floatingFullname"
                                    type="text"
                                    className="form-control"
                                    name="fullName"
                                    placeholder=" "
                                    value={formData.fullName}
                                    onChange={handleChange}
                                    required
                                />
                                <label htmlFor="floatingFullname">Họ và tên</label>
                            </div>
                        </div>
                    </div>
                    <div className="mb-2 password-wrapper form-floating">
                        <input
                            type={showPassword ? "text" : "password"}
                            className="form-control"
                            id="floatingPassword"
                            name="password"
                            placeholder="Nhập mật khẩu"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                        <label htmlFor="floatingPassword">Mật khẩu</label>
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
                    <div className="d-flex justify-content-end mb-3 mt-3">
                        <Link to="/login" className="text-decoration-none text-blue text-decoration-underline">
                            Đã có tài khoản?
                        </Link>
                    </div>
                    <button type="submit" className="btn btn-primary w-100 mt-2">Đăng Ký</button>
                </form>
            </div>
        </div>
    );
};

export default Signup;
