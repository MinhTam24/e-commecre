import { Link } from "react-router-dom";
import React, { use, useEffect } from "react";
import "../css/header.css";
import { useAuth } from "../context/AuthContext";
import { useState } from "react";
import cartApi from "../api/cartApi";
import productDetailApi from "../api/productDetailApi";
import { Toast, ToastContainer } from "react-bootstrap";
import { useCart } from "../context/CartContext";



const Header = () => {
    const { isLoggedIn, userId, logout } = useAuth();
    const [cart, setCart] = useState([]);
    const [cartLocalStorage, setCartLocalStorage] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    const [stockQuantity, setStockQuantity] = useState({});
    const [cartCount, setCartCount] = useState(0);
    const [toasts, setToasts] = useState([]); 
    const { cartItems } = useCart();


 


    const addToast = (message, bg = "warning") => {
        const newToast = { id: Date.now(), message, bg };
    
        setToasts((prevToasts) => {
            let updatedToasts = [...prevToasts, newToast];
            return updatedToasts;
        });
    
        setTimeout(() => {
            removeToast(newToast.id);
        }, 2000);
    };
    

    const removeToast = (id) => {
        setToasts(toasts.filter(toast => toast.id !== id));
    };

       const updateCartCount = () => {
        if(cart){
            const totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
            setCartCount(totalCount)
        }
    };

    useEffect(() => {
        updateCartCount();
        window.addEventListener("updateCartEvent", updateCartCount);
        return () => {
            window.removeEventListener("updateCartEvent", updateCartCount);
        };
    }, [cart]);




    useEffect(() => {
        if (isLoggedIn && userId) {
            setCart(cartItems)
        }
        else {
            setCart(cartLocalStorage);
            for (const item of cart) {
                if (item.quantity > item.productDetailDto.quantity) {
                    setCart(prevCart => prevCart.map(item => item.quantity === item.productDetailDto.quantity))
                }
            }
        }
    }, [cartItems, cartLocalStorage, isLoggedIn])

    //     if (!cartLocalStorage || cartLocalStorage.length === 0) return;
    //     try {
    //         const cartDto = {
    //             accountId: userId,
    //             cartItems: cartLocalStorage.map(item => ({
    //                 name: item.name,
    //                 productDetailDto: {
    //                     id: item.productDetailDto.id, 
    //                     color: item.productDetailDto.color, // Thêm màu sắc
    //                     price: item.productDetailDto.price, // Thêm giá sản phẩm
    //                     stockQuantity: item.productDetailDto.stockQuantity, // Thêm số lượng tồn kho
    //                 },
    //                 size: item.size,
    //                 quantity: item.quantity,
    //             }))
    //         };
    //         console.log("📤 Dữ liệu gửi lên API:", JSON.stringify(cartDto, null, 2));
    //         const response = await cartApi.mergeCart(cartDto);

    //         if (response) {
    //             localStorage.removeItem("cart"); // Xóa giỏ hàng local sau khi merge
    //             setCartLocalStorage([]); // Cập nhật lại state
    //             fetchCartByUserId();
    //         }
    //     } catch (error) {
    //         console.error("Lỗi khi lưu giỏ hàng vào tài khoản:", error);
    //         alert("Có lỗi xảy ra khi lưu giỏ hàng!");
    //     }
    // };

    // const fetchCartByUserId = async () => {
    //     if (isLoggedIn && userId) {
    //         try {
    //             const response = await cartApi.getCartByCustomerId(userId);
    //             setCart(response.cartItems)
    //         } catch (error) {
    //             console.log(error);
    //         }
    //     }
    // };

    useEffect(() => {
        console.log("Giỏ hàng đã cập nhật:", cart);
        if (cart.length > 0) {
            quantisize();
        }
        // updateCartCount();
    }, [cart]);


    useEffect(() => {
        const updateCart = () => {
            const storedCart = localStorage.getItem("cart");
            const parsedCart = storedCart ? JSON.parse(storedCart) : [];
            setCartLocalStorage(parsedCart);
        };

        updateCart();
        window.addEventListener("updateCartEvent", updateCart);

        return () => {
            window.removeEventListener("updateCartEvent", updateCart);
        };
    }, []);

    useEffect(() => {
        setCart(prevCart => {
            const updatedCart = prevCart.map(item => {
                const maxQuantity = stockQuantity[item.productDetailDto.id]?.[item.size] || item.quantity;
                return {
                    ...item,
                    quantity: Math.min(item.quantity, maxQuantity),
                };
            });

            // Chỉ cập nhật nếu có sự thay đổi
            if (JSON.stringify(updatedCart) !== JSON.stringify(prevCart)) {
                return updatedCart;
            }
            return prevCart;
        });
    }, [stockQuantity, setCart]);  // Thêm `setCart` vào dependency để tránh lỗi


    useEffect(() => {
        const totalPrice = cart.reduce((sum, cart) => sum + (cart.productDetailDto.price * cart.quantity), 0);
        setTotalPrice(totalPrice);
    }, [cart]);




    const quantisize =  () => {
        try {
            // Tạo object lưu số lượng theo productId và size
            const quantities = cart.reduce((acc, item) => {
                const stock = item.productDetailDto.stockQuantity;
                console.log(`SIZE ${item.size} CÓ SỐ LƯỢNG: ${stock}`);
    
                if (!acc[item.productDetailDto.id]) acc[item.productDetailDto.id] = {};
                acc[item.productDetailDto.id][item.size] = stock;
    
                return acc;
            }, {});
    
            setStockQuantity(quantities);
        } catch (error) {
            console.error("Lỗi khi lấy quantity", error.response);
        }
    };
    


    const handleQuantityChange = (value, productId, size) => {
        let newQuantity = Number(value);

        // Chặn giá trị không hợp lệ
        if (isNaN(newQuantity) || newQuantity < 1) {
            newQuantity = 1;
        }

        // Kiểm tra tồn kho
        const availableStock = stockQuantity[productId]?.[size] || 0;

        if (newQuantity > availableStock) {
            addToast(`Số lượng tối đa có thể đặt là ${availableStock}`, "warning")
            newQuantity = availableStock; // Giới hạn lại số lượng
        }


        // Cập nhật state chỉ khi số lượng thay đổi
        setCart(prevCart =>
            prevCart.map(item =>
                item.productDetailDto.id === productId && item.size === size
                    ? item.quantity !== newQuantity
                        ? { ...item, quantity: newQuantity }
                        : item
                    : item
            )
        );

    };


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
                        <i className="fa-solid fa-cart-shopping me-3" data-bs-toggle="offcanvas" data-bs-target="#cartOffcanvas">
                            {cartCount > 0 && (
                                <span className="position-absolute top-10 start-90 translate-middle badge rounded-pill bg-danger">
                                    {cartCount}
                                </span>
                            )}
                        </i>
                    </div>
                </div>
                <hr className="row" />
            </header>

            {/* Offcanvas Giỏ hàng */}
            <div className="offcanvas offcanvas-end" id="cartOffcanvas" style={{ width: 500 }} tabIndex="-1">
                <div className="offcanvas-header">
                    <h5 className="offcanvas-title">Giỏ hàng</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="offcanvas"></button>
                </div>

                <div className="offcanvas-body pt-0">
                    <div className="d-flex justify-content-between">
                        <p className="mb-0">Tổng sản phẩm: <span>{cart.length}</span></p>
                        {/* <p>Tổng số lượng: <span>{totalQuantity}</span></p> */}
                    </div>
                    <hr className="mb-2" />
                    <ul id="cartItems" className="list-group">
                        {cart && cart.length > 0 ? (
                            cart.map((cartItem, index) => (
                                <li key={index} className="list-group-item p-0 product-cart d-flex">
                                    <div className="product-cart-image">
                                        <img src={cartItem.imageUrl} alt="Product" />
                                    </div>
                                    <div className="product-cart-infomation">
                                        <p className="product-cart-name mb-0">{cartItem.name}</p>
                                        <p className="fw-light prodcut-cart-price mb-0">
                                            Giá: <span>{cartItem.productDetailDto.price.toLocaleString("vi-VN")}</span> VNĐ
                                        </p>
                                        <div className="d-flex justify-content-between w-50">
                                            <p className="fw-light product-cart-detail mb-0">Size: <span>{cartItem.size}</span></p>
                                            <div className="d-flex align-items-center justify-content-end">
                                                <p className="fw-light product-cart-detail mb-0">Màu:</p>
                                                <div className="ms-2" style={{
                                                    width: 15, height: 15, borderRadius: 2, background: cartItem.productDetailDto.color
                                                }}></div>
                                            </div>
                                        </div>
                                        <div className="product-cart-quantity d-flex">
                                            <p className="fw-light">Số lượng</p>
                                            <button className="btn d-flex align-items-center justify-content-center"
                                                onClick={() => handleQuantityChange(cartItem.quantity - 1, cartItem.productDetailDto.id, cartItem.size)}
                                                disabled={cartItem.quantity <= 1} // Không vượt quá tồn kho

                                            >
                                                <i className="fa-solid fa-minus"></i>
                                            </button>
                                            <input
                                                type="text"
                                                value={cartItem.quantity > cartItem.productDetailDto.quantity ? cartItem.productDetailDto.quantity : cartItem.quantity}
                                                onChange={(e) => handleQuantityChange(e.target.value, cartItem.productDetailDto.id, cartItem.size)}
                                                className="text-center"
                                            />
                                            <button className="btn d-flex align-items-center justify-content-center "
                                                onClick={() => handleQuantityChange(cartItem.quantity + 1, cartItem.productDetailDto.id, cartItem.size)}
                                                disabled={cartItem.quantity >= cartItem.productDetailDto.quantity} // Không vượt quá tồn kho

                                            >
                                                <i className="fa-solid fa-plus"></i>
                                            </button>
                                        </div>
                                        <div className="d-flex justify-content-between align-items-center w-100">
                                            <p className="mb-0 fw-bold">
                                                Tổng tiền: <span className="text-success">
                                                    {(cartItem.productDetailDto.price * cartItem.quantity).toLocaleString("vi-VN")}
                                                </span> VNĐ
                                            </p>
                                            <button
                                                onClick={() => handleDeleteProductCart(cartItem.productDetailDto.id, cartItem.size)}
                                                className="btn btn-danger text-white border me-3"
                                            >
                                                Xóa
                                            </button>
                                        </div>
                                    </div>
                                </li>
                            ))

                        ) : (
                            <div className="align-items-center text-center p-3">
                                <p className="fs-4 fw-bold">Bạn chưa chọn sản phẩm</p>
                                <div className="w-100">
                                    <img className="w-100" src="/img/emptyCart.png" alt="emtycart.png" />
                                </div>
                                <Link to="/products"><button data-bs-dismiss="offcanvas" className="btn btn-secondary w-100 te " style={{ height: 40, fontSize: 20 }}>Trang sản phẩm</button></Link>
                            </div>

                        )}
                    </ul>
                    {cart && cart.length > 0 && (
                        <div>
                            <div className="d-flex justify-content-between mt-3">
                                <p style={{ fontSize: 18 }} className="fw-bold">Tổng Tiền: <span>{totalPrice.toLocaleString("vi-VN")}</span> VNĐ</p>
                                <p onClick={() => handleDeleteAllProductCart()} style={{ cursor: "pointer", textDecoration: "underline", marginTop: 3, marginBottom: 0, color: "red", fontWeight: "lighter" }}>Xóa Tất Cả</p>
                            </div>
                            <button className="btn btn-success w-100 " style={{ height: 50, fontSize: 20 }}>Thanh toán</button>
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

            <ToastContainer position="top-end" className="p-3">
                {toasts.map(toast => (
                    <Toast key={toast.id} bg={toast.bg} onClose={() => removeToast(toast.id)} delay={3000} autohide>
                        <Toast.Body className="fw-bold">{toast.message}</Toast.Body>
                    </Toast>
                ))}
            </ToastContainer>


        </>
    );
};

export default Header;
