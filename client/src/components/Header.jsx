import { Link } from "react-router-dom";
import React, { useEffect } from "react";
import "../css/header.css";
import { useAuth } from "../context/AuthContext";

const Header = () => {
    const { isLoggedIn, setIsLoggedIn, logout } = useAuth();

    useEffect(() => {
        console.log("isLoggedIn changed:", isLoggedIn);
    }, [isLoggedIn]);

    return (
        <>
            <header className="container-fluid">
                <div className="row bg-dark h-25"></div>
                <div className="row h-75">
                    <div className="logo col-3">
                        <Link to="/"><img src="/img/vascara_logo.png" alt="Logo" /></Link>
                    </div>
                    <div className="menu col-6">
                        <nav className="navbar navbar-expand-lg w-100">
                            <div className="container-fluid">
                                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                                    <span className="navbar-toggler-icon" />
                                </button>
                                <div className="collapse navbar-collapse" id="navbarNav">
                                    <ul className="navbar-nav gap-3 text-center p-0">
                                        <li className="nav-item ms-5"><Link className="nav-link" to="/new-arrivals">Hàng mới</Link></li>
                                        <li className="nav-item"><Link className="nav-link" to="/products">Sản phẩm</Link></li>
                                        <li className="nav-item"><Link className="nav-link" to="/collections">Bộ sưu tập</Link></li>
                                        <li className="nav-item"><Link className="nav-link" to="/about">Thông tin</Link></li>
                                        <li className="nav-item"><Link className="nav-link" to="/contact">Liên hệ</Link></li>
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <div className="user-icon d-flex justify-content-end col-3 gap-3">
                    <i className="fa-solid fa-magnifying-glass" data-bs-toggle="offcanvas" data-bs-target="#SearchOffcanvas"></i>
                        <div className="dropdown">
                            <i className="fa-solid fa-user dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"></i>
                            <ul className="dropdown-menu dropdown-menu-end login-dropdown">
                                {isLoggedIn ? (
                                    <>
                                        <li><Link className="dropdown-item btn btn-primary" to="/account">Tài khoản</Link></li>
                                        <li><Link className="dropdown-item btn btn-primary" onClick={() => logout()}>Đăng xuất</Link></li>
                                    </>
                                ) : (
                                    <>
                                        <li><Link className="dropdown-item btn btn-primary" to="/login">Đăng nhập</Link></li>
                                        <li><Link className="dropdown-item btn btn-primary" to="/signup">Đăng ký</Link></li>
                                    </>
                                )}
                            </ul>
                        </div>
                        <i className="fa-solid fa-cart-shopping me-3" data-bs-toggle="offcanvas" data-bs-target="#cartOffcanvas"></i>
                    </div>
                </div>
                <hr className="row" />
            </header>

            {/* Offcanvas Giỏ hàng */}
            <div className="offcanvas offcanvas-end" id="cartOffcanvas" tabIndex="-1">
                <div className="offcanvas-header">
                    <h5 className="offcanvas-title">Giỏ hàng</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="offcanvas"></button>
                </div>
                <div className="offcanvas-body">
                    {isLoggedIn ? (
                        <>
                            <ul id="cartItems" className="list-group">
                                <li className="list-group-item">Sản phẩm 1</li>
                                <li className="list-group-item">Sản phẩm 2</li>
                                <li className="list-group-item">Sản phẩm 3</li>
                            </ul>
                            <button onClick={() => {
                                logout();
                            }} className="btn btn-danger w-100 mt-3">Đăng xuất</button>
                            <button className="btn btn-success w-100 mt-3">Thanh toán</button>
                        </>
                    ) : (
                        <div>
                            <Link to="/login" className="btn btn-primary w-100 " onClick={() => {
                                let offcanvasEl = document.getElementById("cartOffcanvas");
                                let offcanvas = bootstrap.Offcanvas.getInstance(offcanvasEl);
                                if (offcanvas) {
                                    offcanvas.hide(); // Đóng offcanvas
                                }
                            }}>Đăng nhập</Link>
                        </div>
                    )}
                </div>
            </div>
            {/* {offcavas Tìm kiếm} */}
            <div className="offcanvas offcanvas-top" tabIndex="-1" id="SearchOffcanvas" aria-labelledby="offcanvasBottomLabel">
                <div className="offcanvas-header">
                </div>
                <div className="offcanvas-body small">
                    <div className="search mx-auto w-50 d-flex justify-content-center align-items-center">
                        <input className="form-control search-iput" type="text" placeholder="Tìm kiếm..." />
                        <button type="button" className="btn text-reset ms-2 border" data-bs-dismiss="offcanvas" aria-label="Close">Close</button>
                    </div>
                </div>
            </div>

        </>
    );
};

export default Header;
