import ProductCard from "./ProductCard";
import "../css/body.css";

const Body = () => {
    return (<>
        <div className="home-body container p-0">
            <div className="container-fluid banner p-0">
                <div id="carouselExampleControls" className="carousel slide w-100" data-bs-ride="carousel">
                    <div className="carousel-inner">
                        <div className="carousel-item active">
                            <img src="/img/banner.jpg" className="d-block w-100" alt="..." />
                        </div>
                        <div className="carousel-item">
                            <img src="/img/banner1.jpg" className="d-block w-100" alt="..." />
                        </div>
                        <div className="carousel-item">
                            <img src="/img/banner2.png" className="d-block w-100" alt="..." />
                        </div>
                    </div>
                    <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                        <span className="carousel-control-prev-icon" aria-hidden="true" />
                        <span className="visually-hidden">Previous</span>
                    </button>
                    <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                        <span className="carousel-control-next-icon" aria-hidden="true" />
                        <span className="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div className="new-arival container-fluid mt-3">
                <div className="row h-100">
                    <div className="ps-5 col-4" style={{ paddingTop: 120 }}>
                        <p className="m-0 p-0 fs-3 fw-bold">Sản phẩm mới</p>
                        <p className="m-0 p-0 fs-6">Chọn lựa những thiết kết hợp xu hướng</p>
                        <button className="mt-2 btn btn-outline-dark" type="button">Xem thêm</button>
                    </div>
                    <div className="row col-8 ms-2">
                        <div className="d-flex new-arival-card">
                                <ProductCard></ProductCard>
                                <ProductCard></ProductCard>
                                <ProductCard></ProductCard>
                        </div>
                    </div>
                </div>
            </div>
            <div className="blog container-fluid mt-3">
                <div className="row h-100">
                    <div className="col-7 h-100 p-0">
                        <img className="w-100 h-100 " src="banner3.png" alt="..." />
                    </div>
                    <div className="col-5 h-100 p-0 ">
                        <div className="thumnail p-3 pt-0">
                            <img style={{ width: '100%' }} src="banner2.png" alt="..." />
                        </div>
                        <div className="sologan pt-5 ps-5">
                            <p className="m-0" style={{ fontSize: 30, fontWeight: 'bold' }}> DAZED WITH
                                THE DUSK</p>
                            <p> Đắm chìm trong thời khắc chuyển giao giữa ánh sáng và đêm tối, Soirée Glamour như một bản hòa nhạc vang lên để tâm hồn bạn nhảy múa theo những giai điệu trầm bổng. Một nàng thơ Vascara tỏa sáng với vẻ kiều diễm lộng lẫy, thả mình vào đêm dạ tiệc thanh lịch nơi kinh đô ánh sáng.</p>
                            <p className="fs-3 fw-bloder text-decoration-underline">EXPLORE</p>
                        </div>
                    </div>
                </div>
            </div>
            {/* <div className="product-list container-fluid mt-3">
                <p className="fs-3 fw-bold">Sản Phẩm</p>
                <div className="row gap-3">
                    <div className="card text-center col-3 p-0">
                        <img src="/sp.png" className="card-img-top" alt="..." />
                        <div className="card-body">
                            <h5 className="card-title">Balo Nữ</h5>
                            <p className="card-text">200.00</p>
                        </div>
                    </div>
                    <div className="card text-center col-3 p-0">
                        <img src="/sp.png" className="card-img-top" alt="..." />
                        <div className="card-body">
                            <h5 className="card-title">Balo Nữ</h5>
                            <p className="card-text">200.00</p>
                        </div>
                    </div>
                    <div className="card text-center col-3 p-0">
                        <img src="/sp.png" className="card-img-top" alt="..." />
                        <div className="card-body">
                            <h5 className="card-title">Balo Nữ</h5>
                            <p className="card-text">200.00</p>
                        </div>
                    </div>
                    <div className="card text-center col-3 p-0">
                        <img src="/sp.png" className="card-img-top" alt="..." />
                        <div className="card-body">
                            <h5 className="card-title">Balo Nữ</h5>
                            <p className="card-text">200.00</p>
                        </div>
                    </div>
                </div>
            </div> */}
        </div>

    </>)

}

export default Body;