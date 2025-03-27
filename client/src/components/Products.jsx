import "../css/productPage.css"
import ProductCard from "./ProductCard"
import categoryAPi from "../api/categoryApi"
import productApi from "../api/productApi"
import { useEffect, useState } from "react"
import { Link } from "react-router-dom"

const Products = () => {


    const [productList, setProductList] = useState([]);
    const [categories, setCategories] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1); // Tổng số trang
    const [totalElement, setTotalElement] = useState(0);
    const [selectedSort, setSelectedSort] = useState("Mặc định");

    const [filter, setFilter] = useState({
        minPrice: "",
        maxPrice: "",
        sizes: "",
        categoryId: [],
        colors: [],
    });

    const [allColor, setAllColor] = useState([]);

    const pageSize = 7;


    const handleCategoryChange = (categoryId) => {
        setFilter((prev) => ({
            ...prev,
            categoryId: prev.categoryId.includes(categoryId)
                ? prev.categoryId.filter((id) => id !== categoryId)
                : [...prev.categoryId, categoryId],
        }));
    };

    const handleColorChange = (color) => {
        setFilter((prev) => ({
            ...prev,
            colors: prev.colors.includes(color)
                ? prev.colors.filter((c) => c !== color)
                : [...prev.colors, color],
        }));
    };


    const handleSizeChange = (size) => {
        setFilter((prev) => ({
            ...prev,
            sizes: prev.sizes.includes(size)
                ? prev.sizes.filter((sz) => sz !== size)
                : [...prev.sizes, size],
        }));
    };


    const handlePriceChange = (e) => {
        setFilter({
            ...filter,
            [e.target.name]: e.target.value,
        });
    };


    const handleSortChange = (sortBy, sortDirection, label) => {
        setSelectedSort(label)
        setFilter({
            ...filter,
            sortBy,
            sortDirection,
        });
    };


    useEffect(() => {
        const fetchFilteredProducts = async () => {
            try {
                const response = await productApi.getFilterProduct({
                    ...filter,
                    page: currentPage,
                    size: pageSize
                });
                setProductList(response.content);
                setTotalPages(response.totalPages);
                setTotalElement(response.totalElements);
            } catch (error) {
                console.error("Lỗi khi lọc sản phẩm:", error);
            }
        };

        fetchFilteredProducts();
    }, [filter, currentPage]);




    useEffect(() => {
        fetchProductList(currentPage, pageSize);
    }, [currentPage]); // `[]` đảm bảo useEffect chỉ chạy một lần khi component mount

    useEffect(() => {
        fechtAllCategories();
        fetchAllColor();
    }, [])

    const fechtAllCategories = async () => {
        try {
            const response = await categoryAPi.getAllCategories();
            setCategories(response);
        } catch (error) {
            console.log("Lỗi fecht category" + e)
        }
    }

    const fetchAllColor = async () => {
        try {
            const response = await productApi.getAllColor();
            setAllColor(response);
        } catch (error) {
            console.error(error);
        }
    }

    const fetchProductList = async (pageNumber, pageSize) => {
        try {
            const response = await productApi.getAllProduct(pageNumber, pageSize);
            setProductList(response.content);
            setTotalPages(response.totalPages);
            setTotalElement(response.totalElements)
            handleSortChange ("name", "desc", "Mặc định");
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <>
            <div>
                {/* Thanh tiêu đề */}
                <div className="head-line text-white text-center ps-3 pt-3">
                    <nav aria-label="breadcrumb">
                        <ol className="breadcrumb">

                            <li className="breadcrumb-item"><Link to="/">Home</Link></li>
                            <li className="breadcrumb-item active" aria-current="page">all-product</li>
                        </ol>
                    </nav>
                </div>
                <hr className="row m-0" />
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-3 p-0 ">
                            {/* Sidebar */}
                            <nav className=" col-lg-2 bg-light filter-side-bar  p-0 w-100">
                                {/* Danh mục sản phẩm */}
                                <div className="accordion" id="filterAccordion">
                                    {/* Danh mục sản phẩm */}
                                    <div className="accordion-item">
                                        <h2 className="accordion-header" id="headingCategory">
                                            <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseCategory">
                                                Danh mục sản phẩm
                                            </button>
                                        </h2>
                                        <div id="collapseCategory" className="accordion-collapse collapse show">
                                            <div className="accordion-body d-flex flex-wrap">
                                                {categories && categories.length > 0 ? (
                                                    categories.map((category, index) => (
                                                        <div className="form-check col-6 mb-3" key={index}>
                                                            <input type="checkbox" className="form-check-input" id={`category-${index}`} style={{ width: 20, height: 20 }}
                                                                onChange={() => handleCategoryChange(category.id)} />
                                                            <label htmlFor={`category-${index}`}>{category.name}</label>
                                                        </div>))
                                                ) : (
                                                    <div className="form-check">
                                                        <p>Đang tải danh mục sản phẩm</p>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                    {/* Thương hiệu */}
                                    <div className="accordion-item">
                                        <h2 className="accordion-header" id="headingBrand">
                                            <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseBrand">
                                                Màu sắc
                                            </button>
                                        </h2>
                                        <div id="collapseBrand" className="accordion-collapse collapse">
                                            <div className="accordion-body">
                                                {allColor && allColor.length > 0 ? (
                                                    allColor.map((color, index) => (
                                                        <div className="form-check d-flex align-items-center gap-2" key={index}>
                                                            <input
                                                                type="checkbox"
                                                                style={{ backgroundColor: color, width: 30, height: 30 }}
                                                                className="form-check-input"
                                                                id={`brand-${index}`}
                                                                onChange={() => handleColorChange(color)} />
                                                            <label htmlFor={`brand-${index}`} className="ms-2">{color}</label>
                                                        </div>
                                                    ))
                                                ) : (
                                                    <div className="form-check">
                                                        <p>Đang tải màu sắc</p>
                                                    </div>
                                                )}
                                            </div>
                                        </div>

                                    </div>
                                    {/* Thương hiệu */}
                                    <div className="accordion-item">
                                        <h2 className="accordion-header" id="size">
                                            <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSize">
                                                Size
                                            </button>
                                        </h2>
                                        <div id="collapseSize" className="accordion-collapse collapse">
                                            <div className="accordion-body">
                                                <div className="form-check">
                                                    <input type="checkbox" className="form-check-input" onChange={() => handleSizeChange("S")} id="S" style={{ width: 20, height: 20 }} />
                                                    <label htmlFor="brand1">Size S</label>
                                                </div>
                                                <div className="form-check">
                                                    <input type="checkbox" className="form-check-input" onChange={() => handleSizeChange("M")} id="M" style={{ width: 20, height: 20 }} />
                                                    <label htmlFor="brand2">Size M</label>
                                                </div>
                                                <div className="form-check">
                                                    <input type="checkbox" className="form-check-input" onChange={() => handleSizeChange("L")} id="L" style={{ width: 20, height: 20 }} />
                                                    <label htmlFor="brand3">Size L</label>
                                                </div>
                                                <div className="form-check">
                                                    <input type="checkbox" className="form-check-input" onChange={() => handleSizeChange("XL")} id="XL" style={{ width: 20, height: 20 }} />
                                                    <label htmlFor="brand3">Size XL</label>
                                                </div>
                                                <div className="form-check">
                                                    <input type="checkbox" className="form-check-input" onChange={() => handleSizeChange("XXL")} id="XXL" style={{ width: 20, height: 20 }} />
                                                    <label htmlFor="brand3">Size XXL</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {/* Giá tiền */}
                                    <div className="accordion-item">
                                        <h2 className="accordion-header" id="headingPrice">
                                            <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapsePrice">
                                                Giá tiền
                                            </button>
                                        </h2>
                                        <div id="collapsePrice" className="accordion-collapse collapse">
                                            <div className="accordion-body">
                                                <div className="mb-3 w-75">
                                                    <label htmlFor="price2" className="form-label">Giá thấp nhất</label>
                                                    <div className="input-group">
                                                        <input type="text" className="form-control" name="minPrice" placeholder="Nhập giá thấp nhất"
                                                            onChange={handlePriceChange} value={filter.minPrice} />
                                                        <span className="input-group-text">VNĐ</span>
                                                    </div>
                                                </div>
                                                <div className="mb-3 w-75">
                                                    <label htmlFor="price3" className="form-label">Giá cao nhất</label>
                                                    <div className="input-group">
                                                        <input type="text" className="form-control" name="maxPrice" placeholder="Nhập giá cao nhất"
                                                            onChange={handlePriceChange} value={filter.maxPrice} />
                                                        <span className="input-group-text">VNĐ</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </nav>
                        </div>
                        <div className="col-md-9 p-0">
                            {/* Nội dung sản phẩm */}
                            <main className=" col-lg-10 p-0 proudct-side w-100">
                                <div className="d-flex justify-content-between align-items-center px-3" style={{ height: 70 }}>
                                    <div className="total-item pt-3">
                                        <p><span>{totalElement}</span> Sản phẩm</p>
                                    </div>
                                    <div className="sort-filter d-flex">
                                        <p style={{ fontSize: 15, fontWeight: 400 }} className="me-3 mt-3 ">Sắp sếp theo:</p>
                                        <div className="btn-group">
                                            <button className="dropdown-toggle border border-dark bg-white" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                               {selectedSort}
                                            </button>
                                            <ul className="dropdown-menu">
                                                <li><a className="dropdown-item" href="#" onClick={() => handleSortChange("name", "desc", "Mặc định")}>Mặc định</a></li>
                                                <li><a className="dropdown-item" href="#" onClick={() => handleSortChange("price", "desc", "Giá giảm dần")}>Giá giảm dần</a></li>
                                                <li><a className="dropdown-item" href="#"  onClick={() => handleSortChange("price", "asc", "Giá tăng dần")}>Giá tăng dần</a></li>
                                                <li><a className="dropdown-item" href="#" onClick={() => handleSortChange("createAt", "desc", "Mới Nhất")}>Mới nhất</a></li>
                                                <li><a className="dropdown-item" href="#"onClick={() => handleSortChange("createAt", "asc", "Cũ Nhất")}>Cũ nhất</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <hr className="m-0" />
                                <div className="product-list d-flex flex-wrap container">
                                    {productList.map((product) =>
                                        product.productDetailFilter?.map((detail) => {
                                            const imageUrl = detail.imageUrl?.[0] || "https://res.cloudinary.com/dopwq7ciu/image/upload/v1742913444/image-not-available_hkgevr.png";
                                            const price = detail.price || 0;
                                            const detailId = detail.id
                                            return (
                                                <ProductCard
                                                    className="col-3"
                                                    key={`${product.id}-${detail.id}`} // Đảm bảo key duy nhất
                                                    data={{ ...product, imageUrl, price, detailId }} // Truyền thông tin chi tiết sản phẩm
                                                />
                                            );
                                        }) || [] // Tránh lỗi nếu productDetailFilter là null
                                    )}

                                </div>
                                <div className="pt-5">
                                    <nav aria-label="Page navigation example">
                                        <ul className="pagination justify-content-center">
                                            {/* Nút Previous */}
                                            <li className={`page-item ${currentPage === 0 ? "disabled" : ""}`}>
                                                <a className="page-link" href="#"
                                                    onClick={() => setCurrentPage(currentPage - 1)}>
                                                    Previous
                                                </a>
                                            </li>

                                            {Array.from({ length: totalPages }, (_, index) => {
                                                if (index >= currentPage - 1 && index <= currentPage + 1) { // Chỉ hiển thị 3 trang xung quanh trang hiện tại
                                                    return (
                                                        <li key={index} className={`page-item ${currentPage === index ? "active" : ""}`}>
                                                            <a className="page-link" href="#" onClick={() => setCurrentPage(index)}>
                                                                {index + 1}
                                                            </a>
                                                        </li>
                                                    );
                                                }
                                                return null;
                                            })}

                                            {/* Nút Next */}
                                            <li className={`page-item ${currentPage >= totalPages - 1 ? "disabled" : ""}`}>
                                                <a className="page-link" href="#"
                                                    onClick={() => setCurrentPage(currentPage + 1)}>
                                                    Next
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>

                                </div>
                            </main>
                        </div>
                    </div>
                </div>
            </div>


        </>
    );

}

export default Products;