import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import About from "./components/About";
import Products from "./components/Products";
import Contact from "./components/Contact";
import Collections from "./components/Collections";

import 'bootstrap/dist/css/bootstrap.min.css';
import Body from "./components/body";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Home />}>
            <Route path="/" element={<Body />} />
            <Route path="/about" element={<About />} />
            <Route path="/products" element={<Products />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/collections" element={<Collections />} />
          </Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
