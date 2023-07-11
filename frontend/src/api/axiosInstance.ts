import axios from 'axios'

const axiosInstance = axios.create({
  // Replace with the actual Spring Boot API URL
  baseURL: 'http://localhost:8080/api/v1/questions'
})

export default axiosInstance
