import React, { createContext, useState, useContext, useEffect } from 'react';
import cartApi from "../api/cartApi";
import { useAuth } from "../context/AuthContext";
import productDetailApi from '../api/productDetailApi';
import { Toast, ToastContainer } from "react-bootstrap";


// Tạo Context
const CartContext = createContext();

// CartProvider cung cấp dữ liệu cho các component con
export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);
    const { isLoggedIn, userId, logout } = useAuth();
    const [loading, setLoading] = useState(false);
    const [toasts, setToasts] = useState([]);


    const addToast = (message, bg = "warning") => {
        const newToast = { id: Date.now(), message, bg };

        setToasts((prevToasts) => {
            let updatedToasts = [...prevToasts, newToast];

            // Nếu có hơn 3 toast, loại bỏ toast cũ nhất
            if (updatedToasts.length > 3) {
                updatedToasts.shift();
            }

            return updatedToasts;
        });

        setTimeout(() => {
            removeToast(newToast.id);
        }, 2000);
    };

    const removeToast = (id) => {
        setToasts(toasts.filter(toast => toast.id !== id));
    };

    // Hàm lấy dữ liệu giỏ hàng từ API

    const addToCart = async (newItem) => {
        if (!userId) { return; }

        const key = `${newItem.productDetailDto.id}_${newItem.size}`;
        const availableQuantity = await productDetailApi.getQuantitySize(newItem.size, newItem.productDetailDto.id);

        if (availableQuantity === undefined) {
            alert("Dữ liệu tồn kho chưa được tải, vui lòng thử lại sau!");
            return;
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        const existingItemIndex = cartItems.findIndex(
            (item) => item.productDetailDto.id === newItem.productDetailDto.id && item.size === newItem.size
        );

        let updatedCartItems = [...cartItems];  // Lưu giỏ hàng hiện tại

        if (existingItemIndex !== -1) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            const existingItem = updatedCartItems[existingItemIndex];
            const newQuantity = existingItem.quantity + newItem.quantity;

            if (newQuantity > availableQuantity) {
                addToast(`Số lượng tối đa có thể thêm là ${availableQuantity}`, "warning");
                return;
            }

            // Cập nhật lại số lượng cho sản phẩm đã có trong giỏ hàng
            updatedCartItems[existingItemIndex].quantity = newQuantity;
            addToast(`Đã thêm sản phẩm`, "success");
        } else {
            if (newItem.quantity > availableQuantity) {
                addToast(`Số lượng tối đa có thể thêm là ${availableQuantity}`, "warning");
                return;
            }
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới vào giỏ
            addToast(`Đã thêm vào giỏ hàng`, "success");
            updatedCartItems.push(newItem);
        }

        // Cập nhật giỏ hàng mới
        setCartItems(updatedCartItems);
        await saveCartToAccount(updatedCartItems);  // Lưu giỏ hàng vào tài khoản
    };

    useEffect(() => {
        if (isLoggedIn && userId) {
            const storedCart = localStorage.getItem("cart"); 
            const cartLocalStorage =  storedCart ? JSON.parse(storedCart) : [];

            if (cartLocalStorage && cartLocalStorage.length > 0) {

                const confirmSave = window.confirm(
                    "Hiện tại bạn có sản phẩm trong giỏ hàng. Bạn có muốn lưu giỏ hàng này vào tài khoản không?"
                );
                if (confirmSave) {
                    saveCartToAccount(cartLocalStorage);
                    addToast("Đã lưu vào tài khoản", "success")
                } else {
                    localStorage.removeItem("cart")
                }
            }
            
            const fetchCart = async () => {
                if (!userId) return; // Không tải nếu chưa đăng nhập
        
                try {
                    setLoading(true);
                    const response = await cartApi.getCartByCustomerId(userId);
                    setCartItems(response.cartItems || []);
                    console.log("Response fetchCart " + JSON.stringify(response, null, 2))
                } catch (error) {
                    console.error(error);
                } finally {
                    setLoading(false);
                }
            };
            fetchCart();
            window.dispatchEvent(new Event("updateCartEvent"));
        }
    }, [isLoggedIn, userId]);


    // Hàm lưu giỏ hàng vào tài khoản
    const saveCartToAccount = async (cart) => {
        try {
            const cartDto = {
                accountId: userId,
                cartItems: cart.map(item => ({
                    name: item.name,
                    productDetailDto: {
                        id: item.productDetailDto.id,
                        color: item.productDetailDto.color,
                        price: item.productDetailDto.price,
                        stockQuantity: item.productDetailDto.stockQuantity,
                    },
                    size: item.size,
                    quantity: item.quantity,
                }))
            };
            console.log("📤 Gửi API mergeCart:", JSON.stringify(cartDto, null, 2));
            const response = await cartApi.mergeCart(cartDto);

            if (response) {
                localStorage.removeItem("cart"); // Xóa giỏ hàng local sau khi merge
            }
        } catch (error) {
            console.error("Lỗi khi lưu giỏ hàng vào tài khoản:", error);
            alert("Có lỗi xảy ra khi lưu giỏ hàng!");
        }
    };



    return (
        <CartContext.Provider value={{ cartItems, addToCart }}>
            {children}
            {/* ToastContainer sẽ luôn hiển thị các Toasts */}
            <ToastContainer position="top-end" className="p-3">
                {toasts.map((toast) => (
                    <Toast key={toast.id} bg={toast.bg} onClose={() => removeToast(toast.id)}>
                        <Toast.Body className='text-white fw-bold'>{toast.message}</Toast.Body>
                    </Toast>
                ))}
            </ToastContainer>
        </CartContext.Provider>
    );
};

// Custom hook để sử dụng CartContext
export const useCart = () => {
    return useContext(CartContext);
};
