import axios from 'axios';
import config from '../config.json';
import queryString from 'query-string';
import { useAuth } from "../context/AuthContext";


const axiosClient = axios.create({
    baseURL: `${config.SERVER_API}`,
    headers: {
        'Content-Type': 'application/json',
    },
    paramsSerializer: params => queryString.stringify(params)
});


axiosClient.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        if (error.response && error.response.status === 403) {
            const { logout } = useAuth();
            logout();
        }
        return Promise.reject(error);
    }
);


axiosClient.interceptors.response.use((response) => {
    if (response && response.data) {
        return response.data
    }
    return response;
}, (error) => {
    const { response } = error;

    if (response && response.status == 401) {
        localStorage.removeItem("token");

        const { logout } = useAuth();
        logout();
        return Promise.reject(error);
    }

    return Promise.reject(error);
});

export default axiosClient;