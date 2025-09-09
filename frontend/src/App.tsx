import { useState } from "react";
import { InsuranceSelection } from "./components/InsuranceSelection";
import CustomerForm  from "./components/CustomerForm";
import type { CustomerData } from "./components/CustomerForm";
import { QuotationDisplay } from "./components/QuotationDisplay";
import type { Quotation } from "./components/QuotationDisplay";
import { PaymentPage } from "./components/PaymentPage";
import { SuccessPage } from "./components/SuccessPage";
import { generateQuotation } from "./utils/mockApi";
import { ToastProvider, useToast } from "./components/ui/toast";
import api from './api.tsx'

type AppStep = "selection" | "form" | "quotation" | "payment" | "success";

function AppContent() {
  const [currentStep, setCurrentStep] = useState<AppStep>("selection");
  const [selectedInsurance, setSelectedInsurance] = useState<string>("");
  const [customerData, setCustomerData] = useState<CustomerData | null>(null);
  const [quotation, setQuotation] = useState<Quotation | null>(null);
  const [isGeneratingQuote, setIsGeneratingQuote] = useState(false);
  const { toast } = useToast();

  const handleInsuranceSelect = (insuranceType: string) => {
    setSelectedInsurance(insuranceType);
    setCurrentStep("form");
  };

  const generateQuotation = async (data: CustomerData): Promise<Quotation | null> => {
      let result;
      try {
        // Add userId to the data object before sending
        const dataWithUserId = { ...data, userId: crypto.randomUUID() };
        console.log("data is = ", dataWithUserId, typeof(dataWithUserId));
        result = await api.post('/api/policies/quote', JSON.stringify(dataWithUserId));
        console.log('quotation generation successful.',result);
        return result.data;
      }
      catch (error) {
        console.log('Server error', error);
        return null;
      }
  }

  const handleFormSubmit = async (data: CustomerData) => {
    setCustomerData(data);
    setIsGeneratingQuote(true);
    
    toast({ type: "info", description: "Generating your personalized quote..." });
    
    try {
      const quote = await generateQuotation(data);
      console.log("data received = ",quote)
      setQuotation(quote);
      setCurrentStep("quotation");
      toast({ type: "success", description: "Your quote has been generated!" });
    } catch (error) {
      toast({ type: "error", description: "Failed to generate quote. Please try again." });
      console.error("Quote generation error:", error);
    } finally {
      setIsGeneratingQuote(false);
    }
  };

  const handleApplyForInsurance = () => {
    setCurrentStep("success");
    toast({ type: "success", description: "Your policy is now active!" });
  };

  const handlePaymentSuccess = () => {
    setCurrentStep("success");
    toast({ type: "success", description: "Payment successful! Your policy is now active." });
  };

  const handleStartOver = () => {
    setCurrentStep("selection");
    setSelectedInsurance("");
    setCustomerData(null);
    setQuotation(null);
  };

  const handleBackToSelection = () => {
    setCurrentStep("selection");
    setSelectedInsurance("");
  };

  const handleBackToForm = () => {
    setCurrentStep("form");
  };

  const handleBackToQuotation = () => {
    setCurrentStep("quotation");
  };

  // Show loading state while generating quote
  if (isGeneratingQuote) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 mx-auto mb-4 border-4 border-primary border-t-transparent rounded-full animate-spin"></div>
          <h2 className="mb-2">Generating Your Quote</h2>
          <p className="text-muted-foreground">
            We're calculating the best rates for you. This may take a few moments...
          </p>
        </div>
      </div>
    );
  }

  return (
    <>
      {currentStep === "selection" && (
        <InsuranceSelection onSelect={handleInsuranceSelect} />
      )}

      {currentStep === "form" && (
        <CustomerForm
          insuranceType={selectedInsurance}
          onSubmit={handleFormSubmit}
          onBack={handleBackToSelection}
        />
      )}

      {currentStep === "quotation" && customerData && quotation && (
        <QuotationDisplay
          customerData={customerData}
          quotation={quotation}
          onApply={handleApplyForInsurance}
          onBack={handleBackToForm}
        />
      )}

      {currentStep === "payment" && customerData && quotation && (
        <PaymentPage
          customerData={customerData}
          quotation={quotation}
          onPaymentSuccess={handlePaymentSuccess}
          onBack={handleBackToQuotation}
        />
      )}

      {currentStep === "success" && customerData && quotation && (
        <SuccessPage
          customerData={customerData}
          quotation={quotation}
          onStartOver={handleStartOver}
        />
      )}
    </>
  );
}

export default function App() {
  return (
    <ToastProvider>
      <AppContent />
    </ToastProvider>
  );
}