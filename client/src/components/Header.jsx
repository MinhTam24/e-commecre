import { Link } from "react-router-dom";  // Import Link from react-router-dom
import "../css/header.css";

const Header = () => {
    return (
        <>
            <header className="container-fluid">
                <div className="row bg-dark h-25">
                    {/* Content for the top section (if needed) */}
                </div>
                <div className="row h-75">
                    <div className="logo col-3">
                        <Link to="/"><img src="/img/vascara_logo.png" alt="Logo" /></Link>
                    </div>
                    <div className="menu col-6">
                        <nav className="navbar navbar-expand-lg w-100">
                            <div className="container-fluid">
                                {/* Toggle button for small screens */}
                                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span className="navbar-toggler-icon" />
                                </button>
                                {/* Menu items */}
                                <div className="collapse navbar-collapse" id="navbarNav">
                                    <ul className="navbar-nav gap-3 text-center p-0">
                                        <li className="nav-item ms-5">
                                            <Link className="nav-link" to="/new-arrivals">Hàng mới</Link>
                                        </li>
                                        <li className="nav-item">
                                            <Link className="nav-link" to="/products">Sản phẩm</Link>
                                        </li>
                                        <li className="nav-item">
                                            <Link className="nav-link" to="/collections">Bộ sưu tập</Link>
                                        </li>
                                        <li className="nav-item">
                                            <Link className="nav-link" to="/about">Thông tin</Link>
                                        </li>
                                        <li className="nav-item">
                                            <Link className="nav-link" to="/contact">Liên hệ</Link>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <div className="user-icon col-3">
                        {/* Content for the user icon (if needed) */}
                    </div>
                </div>
                <hr className="row" />
            </header>
        </>
    );
}

export default Header;
