import axiosClient from './axiosClient'; 

const accountApi =  {

    login: (loginDto) => {
        try {
          return axiosClient.post('/api/login', loginDto);
        } catch (error) {
          throw error;
        }
      },

    singUp: (registerDto) => {
      try {
        return axiosClient.post('/api/register', registerDto);
      } catch (error) {
        throw error;
      }
    }
  
}

export default accountApi;
