import axiosClient from "./axiosClient";

const productApi = {

    getProducts: (params) => {
        return axiosClient.get(`/api/products`, { params });
    }


}