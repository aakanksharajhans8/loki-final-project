// src/api/apiService.ts
import axios from 'axios';
import type { Policy, Product } from '../types/insurance';

// This is the base URL of your Spring Boot backend.
// Make sure the port (8083) matches your `application.properties`.
const API_BASE_URL = 'http://localhost:8083/api/v1';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Function to fetch all products
export const getAllProducts = async (): Promise<Product[]> => {
    const response = await apiClient.get('/products');
    return response.data;
};

// Function to fetch a policy by its number
export const getPolicyByNumber = async (policyNumber: string): Promise<Policy> => {
    const response = await apiClient.get(`/policies/${policyNumber}`);
    return response.data;
};

// Function to fetch all policies for a given customer
export const getPoliciesByCustomerId = async (customerId: string): Promise<Policy[]> => {
    const response = await apiClient.get(`/policies/customer/${customerId}`);
    return response.data;
};
