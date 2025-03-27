import { useEffect, useState } from "react";
import "../css/productCard.css";
import { Link } from "react-router-dom"

const ProductCard = ({ data }) => {


    if (!data) return <p>Không có dữ liệu sản phẩm</p>;

    return (
        <div className="product-card">
            <Link to={`/product/${data.id}/${data.detailId}`} className="text-decoration-none">
                <div className="card text-center col p-0">
                    <img src={data.imageUrl} className="card-img-top" alt={data.name} />
                    <div className="card-body">
                        <h5 className="card-title">{data.name}</h5>
                        <p className="card-text">{data.price} VNĐ</p>
                    </div>
                </div>
            </Link>
        </div>
    );
};

export default ProductCard;
