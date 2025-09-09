import type { CustomerData } from "../components/CustomerForm";
import type { Quotation } from "../components/QuotationDisplay";

// Mock API function to generate insurance quotation
export const generateQuotation = async (customerData: CustomerData): Promise<Quotation> => {
  // Simulate API delay
  await new Promise(resolve => setTimeout(resolve, 2000));

  const { customerAge, customerIncome, insuranceType, customerGender } = customerData;
  const age = parseInt(customerAge);
  const income = parseInt(customerIncome);
  
  // Base premium calculation logic (simplified)
  let basePremium = 0;
  let coverageAmount = 0;
  let deductible = 0;

  switch (insuranceType) {
    case "life":
      basePremium = 50;
      coverageAmount = income * 10; // 10x annual income
      deductible = 0;
      
      // Age factor
      if (age > 50) basePremium *= 2;
      else if (age > 35) basePremium *= 1.5;
      
      // Gender factor (simplified)
      if (customerGender === "male") basePremium *= 1.1;
      
      break;

    case "health":
      basePremium = 200;
      coverageAmount = 500000; // Fixed coverage
      deductible = 2500;
      
      // Age factor
      if (age > 50) basePremium *= 2.5;
      else if (age > 35) basePremium *= 1.8;
      
      // Income factor
      if (income < 30000) basePremium *= 0.8;
      else if (income > 100000) basePremium *= 1.2;
      
      break;

    case "vehicle":
      basePremium = 100;
      coverageAmount = 250000; // Fixed coverage
      deductible = 1000;
      
      // Age factor (younger drivers pay more)
      if (age < 25) basePremium *= 2;
      else if (age < 35) basePremium *= 1.3;
      
      // Vehicle type factor
      const vehicleMultiplier = {
        car: 1,
        motorcycle: 1.5,
        truck: 1.2,
        suv: 1.1,
        van: 1.15
      };
      basePremium *= vehicleMultiplier[customerData.vehicleType as keyof typeof vehicleMultiplier] || 1;
      
      break;
  }

  const monthlyPremium = Math.round(basePremium);
  const annualPremium = Math.round(monthlyPremium * 12 * 0.9); // 10% discount for annual payment

  // Generate quote expiry date (30 days from now)
  const validUntil = new Date();
  validUntil.setDate(validUntil.getDate() + 30);

  return {
    quoteId: `Q-${Date.now().toString().slice(-6)}-${Math.random().toString(36).substr(2, 4).toUpperCase()}`,
    monthlyPremium,
    annualPremium,
    coverageAmount,
    deductible,
    validUntil: validUntil.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  };
};

// Mock API function for payment processing
export const processPayment = async (paymentData: any): Promise<{ success: boolean; transactionId?: string; error?: string }> => {
  // Simulate API delay
  await new Promise(resolve => setTimeout(resolve, 3000));

  // Mock payment processing (always succeeds for demo)
  return {
    success: true,
    transactionId: `TXN-${Date.now().toString().slice(-8)}`
  };
};