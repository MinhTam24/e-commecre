import { Link } from "react-router-dom";  // Đảm bảo bạn đã import Link từ react-router-dom
import Header from "./Header";
import Body from "./body";
import { BrowserRouter as Router, Routes, Route, Outlet } from 'react-router-dom';


const Home = () => {
    return (
        <>  
            <Header></Header>
            <Outlet/>
        </>
    );

}

export default Home;