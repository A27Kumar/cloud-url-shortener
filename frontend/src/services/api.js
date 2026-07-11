import axios from "axios";

const API = axios.create({
    baseURL: "/api/urls"
});

export default API;