import "../css/productDetail.css";
import productApi from "../api/productApi";
import productDetailApi from "../api/productDetailApi";

import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const ProductDetail = () => {
    const { id , detailId } = useParams();
    const [product, setProduct] = useState(null); // ✅ Đặt giá trị ban đầu là `null`
    const [productDetail, setProductDetail] = useState(null);
    const [selectedImage, setSelectedImage] = useState(null);
    const [sizes, setSize] = useState([]);
    const [selectedColor, setSelectedColor] = useState(null);
    const [selectedSize, setSelectedSize] = useState(null);
    const [quantity, setQuantity] = useState(1);
    const [quantitySize, setQuantitySize] = useState(0);
    const [expanded, setExpanded] = useState(false);
    const [lines, setLine] = useState([]);
    const previewLines = 3;



    useEffect(() => {
        fetchProductById();
        fectchProductDetailById(detailId);
    }, [id, detailId]);


    // useEffect(() => {
    //     if (product && product.productDetail && product.productDetail.length > 0) {
    //         fectchProductDetailById(product.productDetail[0]);
    //         setLine(product.description.split("\n"));
    //     }
    // }, [product]);


    

    useEffect(() => {
        if (productDetail) {
            console.log("Updated productDetail:", productDetail);
        }
        if (product) {
            console.log(product)

        }
    }, [productDetail, product]);

    // const handleAddToCart = () => {
    //     const cartItem = {
    //         IdSanPHam: id,
    //         IdChiTietSanPham: productDetail.id,
    //         Size: selectedSize,
    //         Color: selectedColor,
    //         Quantity: quantity
    //     };
    //     const cartItems = JSON.parse(localStorage.getItem("CartItems")) || [];
    //     cartItems.push(cartItem);
    //     localStorage.setItem("CartItems", JSON.stringify(cartItems));
    // };

    useEffect(() => {
        if (selectedSize && selectedSize != null) {
            fecthQuanTitySize(selectedSize, productDetail.id)
            setQuantity(1)
        }
    }, [selectedSize]);

    const fecthQuanTitySize = async (size, id) => {
        try {
            const respone = await productDetailApi.getQuantitySize(size, id);
            setQuantitySize(respone);
            console.log("SIZE CÓ SỐ LƯỢNG" + respone)
        } catch (error) {
            console.error("Lỗi khi lấy quantity", error.response);

        }
    }


    const fectchProductDetailByColor = async (color, id) => {
        try {
            const respone = await productDetailApi.getProductDetailByColor(color, id);
            setProductDetail(respone);
            setSelectedImage(respone.imageUrl[0]);
            setSize(respone.size);
        } catch (error) {
            console.error("Lỗi khi lấy sản phẩm bằng màu", error.response);
        }
    }

    const fectchProductDetailById = async (id) => {
        try {
            const respone = await productDetailApi.getProductDetailById(id);
            setSelectedImage(respone.imageUrl[0])
            setSize(respone.size);
            setProductDetail(respone)
            setSelectedColor(respone.color)
        } catch (error) {
        }
    }

    const fetchProductById = async () => {
        try {
            const response = await productApi.getProductById(id); // ✅ Thêm `await`
            setProduct(response);
        } catch (error) {
            console.error("Lỗi khi lấy sản phẩm:", error.response);
        }
    };

    const handleQuantityChange = (value) => {
        if (/^\d*$/.test(value)) { // Chỉ cho phép số nguyên
            let newQuantity = Math.max(1, Math.floor(Number(value) || 1)); // Chỉ lấy số >= 1
            if (quantitySize) {
                newQuantity = Math.min(newQuantity, quantitySize); // Không vượt quá quantitySize
            }
            setQuantity(newQuantity);
        }
    };

    const handleGetProductDetailByColor = (color, id) => {
        setSelectedColor(color)
        setSelectedSize(null);
        fectchProductDetailByColor(color, id)
    }

    if (!product) {
        return (

            <div className="d-flex justify-content-center " style={{ height: 400 }}>
                <div className="text-center" style={{ width: 60, height: 60, marginTop: 150 }}>
                    <div className="spinner-border" role="status">
                        <span className="visually-hidden" style={{ fontSize: 300 }}>Loading...</span>
                    </div>
                    <p>Loading...</p>
                </div>
            </div>

        );
    }
    return (
        <>
            <nav aria-label="breadcrumb">
                <ol className="breadcrumb pt-3 ps-3">
                    <li className="breadcrumb-item"><a href="#">Home</a></li>
                    <li className="breadcrumb-item"><a href="#">Library</a></li>
                    <li className="breadcrumb-item active" aria-current="page">{product.name}</li>
                </ol>
            </nav>
            <hr />
            <div className="container">
                <div className="row">
                    <div className="image-side col-md-7 d-flex">
                        <div className="list-image-col col-2">
                            {productDetail && productDetail.imageUrl && productDetail.imageUrl.length > 0 && (
                                productDetail.imageUrl.map((img, index) => (
                                    <div className="small-imagebox" key={index} onClick={() => setSelectedImage(img)}>
                                        <img src={img} alt="Sản phẩm" />
                                    </div>
                                ))
                            )}
                        </div>
                        <div className="image-box col-10">
                            <img src={selectedImage} alt={product.name || "Sản phẩm"} />
                        </div>
                    </div>
                    <div className="infomation-box col-md-5">
                        <div className="pt-3">
                            <div className="d-flex justify-content-between align-items-center">
                                <p className="product-name mb-0">{product.name}</p>
                                <p className="mb-0">Số lượng: <span>{product.totalQuantity}</span></p>
                            </div>
                            <p className="product-price"><span>{productDetail?.price || "Đang cập nhật"}</span> VNĐ</p>
                        </div>
                        <div>
                            <p>Size</p>
                            <div className="ps-3" style={{ height: 40 }}>
                                {product.size && product.size.length > 0 ? (
                                    product.size.map((sz, index) => (
                                        <button
                                            className={`button-size ${selectedSize === sz ? "button-selected" : ""}`}
                                            disabled={!sizes?.includes(sz)}
                                            key={index}
                                            onClick={() => setSelectedSize(sz)}
                                        >{sz}</button>
                                    ))
                                ) : (<p>Không có Size</p>)}
                            </div>
                        </div>
                        <div className="pt-3">
                            <p>Color</p>
                            <div className="ps-3">
                                {product.color && product.color.length > 0 ? (
                                    product.color.map((clr, index) => (
                                        <button
                                            key={index}
                                            className="color-btn"
                                            onClick={() => handleGetProductDetailByColor(clr, id)}
                                            style={{ backgroundColor: clr }}
                                        />
                                    ))
                                ) : (
                                    <p>Không có màu sắc</p>
                                )}
                            </div>
                        </div>
                        <div className="pt-3 d-flex justify-content-between">
                            <p>Số lượng</p>
                            <div className="gap-0 text-align-center">
                                <button className="button-quantity" disabled={quantity <= 1} // Không giảm nếu số lượng = 1
                                    onClick={() => setQuantity(quantity - 1)}><i className="fa-solid fa-minus" /></button>
                                <input className="quantity-Input" type="text" value={quantity} onChange={(e) => handleQuantityChange(e.target.value)}
                                    min="1" />
                                <button
                                    className="button-quantity"
                                    disabled={!selectedSize || quantity >= quantitySize}
                                    onClick={() => setQuantity(quantity + 1)}
                                >
                                    <i className="fa-solid fa-plus" />
                                </button>
                            </div>
                        </div>
                        <div className="pt-3 d-flex flex-column gap-2 button-buy">
                            <button className="btn-addToCard" onClick={() => handleAddToCart()}>Thêm Vào Giỏ Hàng</button>
                            <button className="btn-buyNow">Mua Ngay</button>
                        </div>
                    </div>
                </div>
                <div className="decription pt-3 h-100 mt-3">
                    <div className="container">
                        <p style={{ fontSize: 20, fontWeight: "bold" }}>Mô tả sản phẩm</p>
                        <div className="h-auto">
                            {lines.slice(0, expanded ? lines.length : previewLines).map((line, index) => (
                                <p className="mb-0" key={index}>{line}</p>
                            ))}
                        </div>
                        <div className="text-center">
                            {/* ✅ Nút Xem thêm / Thu gọn */}
                            {lines.length > previewLines && (
                                <button
                                    className="btn p-0 mt-2"
                                    style={{ width: 160, height: 40 }}
                                    onClick={() => setExpanded(!expanded)}
                                >
                                    {expanded ? "Thu gọn" : "Xem thêm"}
                                </button>
                            )}
                        </div>

                    </div>
                </div>
            </div>

        </>
    );
};

export default ProductDetail;
