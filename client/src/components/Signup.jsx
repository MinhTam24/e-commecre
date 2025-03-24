import { useState } from "react";
import "../css/signup.css";
import { Link } from "react-router-dom";  // Import Link from react-router-dom


const Signup = () => {

    const [showPassword, setShowPassword] = useState(false);

    return (
        <>
            <div className="d-flex justify-content-center align-items-center signup-page">
                <div className="register-container">
                    <h2 className="text-center mb-3 fw-bold">Đăng Ký</h2>
                    <form>
                        <div className="mb-2 form-floating">
                            <input id="floatingUsername" type="text" className="form-control" placeholder="Nhập số điện thoại hoặc email" required />
                            <label for="floatingUsername" className="form-label">SĐT hoặc Email</label>
                        </div>
                        <div className="row form-floating mb-2">
                            <div className="col-md-6  ">
                                < div className="form-floating">
                                    <input id="floatingName" type="text" className="form-control" placeholder=" " required />
                                    <label for="floatingName" >Tên</label>
                                </div>
                            </div>
                            <div className="col-md-6 form-floating">
                                <div className="form-floating">
                                    <input id="floatingFullname" type="text" className="form-control" placeholder=" " required />
                                    <label for="floatingFullname" className="form-label">Họ và tên</label>
                                </div>
                            </div>
                        </div>
                        <div className="mb-2 password-wrapper  form-floating">
                            <input type={showPassword ? "text" : "password"}
                                className="form-control" id="floatingPassword" placeholder="Nhập mật khẩu" required />
                            <label className="form-label" for="floatingPassword" >Mật khẩu</label>
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
                            {/* <a href="login.html" className="text-decoration-none text-blue text-decoration-underline">Đã có tài khoản</a> */}
                            <Link to="/login" className="text-decoration-none text-blue text-decoration-underline" >Đã có tài khoản</Link>
                        </div>
                        <button type="submit" className="btn btn-primary w-100 mt-2">Đăng Ký</button>
                    </form>
                </div>
            </div>

        </>
    )

}

export default Signup;