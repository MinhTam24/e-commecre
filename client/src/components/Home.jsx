import { Link } from "react-router-dom";  // Đảm bảo bạn đã import Link từ react-router-dom
import Header from "./Header";
import Body from "./body";
import { BrowserRouter as Router, Routes, Route, Outlet } from 'react-router-dom';
import Footer from "./Footer";
import "../css/home.css"


const Home = () => {
    return (
        <div className="home-container">
            <Header />
            <main className="content">
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}

export default Home;
