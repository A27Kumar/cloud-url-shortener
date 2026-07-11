import axios from "axios";

const API = axios.create({
    baseURL: "http://52.66.241.120:8080/api/urls"
});

export default API;