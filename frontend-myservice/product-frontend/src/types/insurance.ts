// src/types/insurance.ts

export interface Endorsement {
    id: number;
    effectiveDate: string; // Dates are typically strings in JSON
    description: string;
    changeDetails: string;
    createdAt: string;
}

export interface ProductCoverage {
    id: number;
    coverageCode: string;
    name: string;
    description: string;
    coverageLimit: number;
    deductible: number;
}

export interface Product {
    id: number;
    productCode: string;
    name: string;
    description: string;
    productType: string;
    active: boolean;
    coverages: ProductCoverage[];
    createdAt: string;
}

export interface Policy {
    id: number;
    policyNumber: string;
    productId: number;
    customerId: string;
    startDate: string;
    endDate: string;
    status: string;
    premiumAmount: number;
    endorsements: Endorsement[];
    createdAt: string;
}