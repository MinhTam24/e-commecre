import axiosClient from './axiosClient';

const productDetailApi = {


    getProductDetailById: (id) => {
        try {
            return axiosClient.get(`/api/productDetail/${id}`)

        } catch (error) {
            console.error("Lỗi khi lấy productDetail theo id:", error);
            throw error;
        }
    },

    getProductDetailByColor: (color, id) => {
        try {
            return axiosClient.get("/api/productDetail/color", {
                params: { color, id }
            });
        } catch (error) {
            console.error("Lỗi khi lấy productDetail theo màu:", error);
            throw error;
        }
    },

    getQuantitySize: (size, id) => {
        try {
            return axiosClient.get("/api/productDetail/quantity", {
                params: { size, id }
            });
        } catch (error) {
            console.error("Lỗi khi lấy quantity productDetail:", error);
            throw error;
        }
    },



    getProductDetailBySize: (size, id) => {
        try {
            return axiosClient.get("/api/productDetail/size", {
                params: { size, id }
            });
        } catch (error) {
            console.error("Lỗi khi lấy productDetail theo Size:", error);
            throw error;
        }
    }



};

export default productDetailApi;
