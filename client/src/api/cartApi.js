import axiosClient from "./axiosClient"



const cartAPi = {

    getCartById: (id) => {
        try {
            return axiosClient.get(`/api/cart/${id}`)
        } catch (error) {
            return error;
        }
    },

    mergeCart: (cartDto) => {
        try {
            return axiosClient.put(`/api/cart/merge`, cartDto);
        } catch (error) {
            return Promise.reject(error);
        }
    },


    getCartByCustomerId: (id) => {
        try {
            return axiosClient.get(`/api/cart/customer/${id}`)
        } catch (error) {
            return error;
        }
    },


    deleteCart: (id) => {
        try {
            return axiosClient.delete(`/api/cart/delete/${id}`)
        } catch (error) {
            return error;
        }
    }


}

export default cartAPi;