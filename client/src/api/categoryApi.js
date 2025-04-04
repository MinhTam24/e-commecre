import axiosClient from "./axiosClient"


const categoryAPi = {

getAllCategories: () => {
    return axiosClient.get("/api/category")    
}


}

export default categoryAPi