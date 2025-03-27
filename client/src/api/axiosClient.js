import axios from 'axios';
import config from '../config.json';
import queryString from 'query-string';
import { useNavigate } from 'react-router-dom';


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
            localStorage.removeItem('token');
            const navigate = useNavigate(); // Dùng useNavigate để điều hướng
            navigate('/login'); // Chuyển về trang login
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

    // loi 401 token het han xoa token khoi localstorage
    if (response && response.status == 401) {
        localStorage.removeItem("token");
        const navigate = useNavigate(); // Dùng useNavigate để điều hướng
        navigate('/login'); // Chuyển về trang login
        return Promise.reject(error);
    }

    return Promise.reject(error);
});

export default axiosClient;