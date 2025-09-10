import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api/payments", // backend Spring Boot URL
});

// Create Razorpay order
export const createOrder = (data) => API.post("/create-order", data);

// Verify payment after success
export const verifyPayment = (data) => API.post("/verify", data);

// Get all transactions
export const getTransactions = () => API.get("/transactions");

// Get receipt by transaction id
export const getReceipt = (id) => API.get(`/receipt/${id}`, { responseType: "blob" });
