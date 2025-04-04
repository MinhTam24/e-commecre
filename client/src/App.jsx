import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import About from "./components/About";
import Products from "./components/Products";
import Contact from "./components/Contact";
import Collections from "./components/Collections";

import 'bootstrap/dist/css/bootstrap.min.css';
import Body from "./components/body";
import Login from "./components/login";
import Signup from "./components/Signup";
import { AuthProvider } from "./context/AuthContext";
import ProductDetail from "./components/ProductDetail";

function App() {
  
  return (
    <div className="App">
        <Routes>
          <Route path="/" element={<Home />}>
            <Route path="/" element={<Body />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/about" element={<About />} />
            <Route path="/products" element={<Products />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/collections" element={<Collections />} />
            <Route path="/product/:id/:detailId" element={<ProductDetail />} />
          </Route>
        </Routes>
    </div>
  );
}

export default App;
