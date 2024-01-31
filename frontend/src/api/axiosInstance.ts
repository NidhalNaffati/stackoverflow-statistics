import axios from 'axios'

const springBootAPI = import.meta.env.VITE_SPRING_APP_URL + '/api/v1/questions'

const axiosInstance = axios.create({
  // Replace with the actual Spring Boot API URL
  baseURL: springBootAPI
})

export default axiosInstance
