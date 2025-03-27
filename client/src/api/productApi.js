import axiosClient from "./axiosClient";

const productApi = {

    getProducts: (params) => {
        return axiosClient.get(`/api/products`, { params });
    },

    getAllProduct: (pageNumber, pageSize) => {
        return axiosClient.get(`/api/product/all`, {
            params: { pageNumber, pageSize }
        });
    },


    getProductById: (id) => {
        return axiosClient.get(`/api/product/${id}`)
    },

    getAllColor: () => {
        return axiosClient.get("/api/all-color")
    },

    getFilterProduct: (filters) => {
        return axiosClient.get(`/api/products/filter`, { params: filters });
    }

}

export default productApi;