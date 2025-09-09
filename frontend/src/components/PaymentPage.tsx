import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { RadioGroup, RadioGroupItem } from "./ui/radio-group";
import { ArrowLeft, CreditCard, Lock, Shield } from "lucide-react";
import type { CustomerData } from "./CustomerForm";
import type { Quotation } from "./QuotationDisplay";

interface PaymentPageProps {
  customerData: CustomerData;
  quotation: Quotation;
  onPaymentSuccess: () => void;
  onBack: () => void;
}

export function PaymentPage({ customerData, quotation, onPaymentSuccess, onBack }: PaymentPageProps) {
  const [paymentMethod, setPaymentMethod] = useState("card");
  const [paymentFrequency, setPaymentFrequency] = useState("monthly");
  const [paymentData, setPaymentData] = useState({
    cardNumber: "",
    expiryDate: "",
    cvv: "",
    cardHolder: "",
    billingAddress: "",
    city: "",
    zipCode: ""
  });
  const [isProcessing, setIsProcessing] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});

  const amount = paymentFrequency === "monthly" ? quotation.monthlyPremium : quotation.annualPremium;
  const savings = paymentFrequency === "annual" ? (quotation.monthlyPremium * 12 - quotation.annualPremium) : 0;

  const validatePaymentForm = () => {
    const newErrors: Record<string, string> = {};

    if (!paymentData.cardNumber || paymentData.cardNumber.length < 16) {
      newErrors.cardNumber = "Valid card number is required";
    }

    if (!paymentData.expiryDate || !/^\d{2}\/\d{2}$/.test(paymentData.expiryDate)) {
      newErrors.expiryDate = "Valid expiry date is required (MM/YY)";
    }

    if (!paymentData.cvv || paymentData.cvv.length < 3) {
      newErrors.cvv = "Valid CVV is required";
    }

    if (!paymentData.cardHolder.trim()) {
      newErrors.cardHolder = "Card holder name is required";
    }

    if (!paymentData.billingAddress.trim()) {
      newErrors.billingAddress = "Billing address is required";
    }

    if (!paymentData.city.trim()) {
      newErrors.city = "City is required";
    }

    if (!paymentData.zipCode.trim()) {
      newErrors.zipCode = "ZIP code is required";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validatePaymentForm()) {
      return;
    }

    setIsProcessing(true);
    
    // Simulate payment processing
    await new Promise(resolve => setTimeout(resolve, 3000));
    
    // Mock payment success (in real app, this would be an API call)
    onPaymentSuccess();
  };

  const updateField = (field: string, value: string) => {
    setPaymentData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: "" }));
    }
  };

  const formatCardNumber = (value: string) => {
    const cleaned = value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
    const formatted = cleaned.match(/.{1,4}/g)?.join(' ') || cleaned;
    return formatted.substring(0, 19);
  };

  const formatExpiryDate = (value: string) => {
    const cleaned = value.replace(/\D/g, '');
    if (cleaned.length >= 2) {
      return cleaned.substring(0, 2) + '/' + cleaned.substring(2, 4);
    }
    return cleaned;
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12">
      <div className="container mx-auto px-4 max-w-4xl">
        <Button
          variant="ghost"
          onClick={onBack}
          className="mb-6"
        >
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Quote
        </Button>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Order Summary */}
          <div className="lg:col-span-1">
            <Card>
              <CardHeader>
                <CardTitle>Order Summary</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <p className="text-sm text-muted-foreground">Policy Holder</p>
                  <p className="font-medium">{customerData.customerName}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Insurance Type</p>
                  <p className="font-medium capitalize">{customerData.insuranceType} Insurance</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Quote ID</p>
                  <p className="font-medium">{quotation.quoteId}</p>
                </div>

                <div className="border-t pt-4">
                  <RadioGroup
                    value={paymentFrequency}
                    onValueChange={setPaymentFrequency}
                    className="space-y-3"
                  >
                    <div className="flex items-center space-x-2 p-3 border rounded-lg">
                      <RadioGroupItem value="monthly" id="monthly" />
                      <Label htmlFor="monthly" className="flex-1">
                        <div>Monthly Payment</div>
                        <div className="text-sm text-muted-foreground">
                          ${quotation.monthlyPremium.toLocaleString()}/month
                        </div>
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2 p-3 border rounded-lg">
                      <RadioGroupItem value="annual" id="annual" />
                      <Label htmlFor="annual" className="flex-1">
                        <div>Annual Payment</div>
                        <div className="text-sm text-muted-foreground">
                          ${quotation.annualPremium.toLocaleString()}/year
                        </div>
                        {savings > 0 && (
                          <div className="text-sm text-green-600">
                            Save ${savings.toLocaleString()}
                          </div>
                        )}
                      </Label>
                    </div>
                  </RadioGroup>
                </div>

                <div className="border-t pt-4">
                  <div className="flex justify-between items-center text-lg font-bold">
                    <span>Total Today:</span>
                    <span className="text-primary">${amount.toLocaleString()}</span>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Payment Form */}
          <div className="lg:col-span-2">
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <CreditCard className="w-5 h-5" />
                  Payment Information
                </CardTitle>
                <div className="flex items-center gap-2 text-sm text-muted-foreground">
                  <Lock className="w-4 h-4" />
                  Your payment information is encrypted and secure
                </div>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="space-y-6">
                  {/* Card Information */}
                  <div className="space-y-4">
                    <div>
                      <Label htmlFor="cardNumber">Card Number</Label>
                      <Input
                        id="cardNumber"
                        value={paymentData.cardNumber}
                        onChange={(e) => updateField("cardNumber", formatCardNumber(e.target.value))}
                        placeholder="1234 5678 9012 3456"
                        className={errors.cardNumber ? "border-destructive" : ""}
                      />
                      {errors.cardNumber && (
                        <p className="text-sm text-destructive mt-1">{errors.cardNumber}</p>
                      )}
                    </div>

                    <div className="grid grid-cols-3 gap-4">
                      <div className="col-span-2">
                        <Label htmlFor="expiryDate">Expiry Date</Label>
                        <Input
                          id="expiryDate"
                          value={paymentData.expiryDate}
                          onChange={(e) => updateField("expiryDate", formatExpiryDate(e.target.value))}
                          placeholder="MM/YY"
                          className={errors.expiryDate ? "border-destructive" : ""}
                        />
                        {errors.expiryDate && (
                          <p className="text-sm text-destructive mt-1">{errors.expiryDate}</p>
                        )}
                      </div>
                      <div>
                        <Label htmlFor="cvv">CVV</Label>
                        <Input
                          id="cvv"
                          value={paymentData.cvv}
                          onChange={(e) => updateField("cvv", e.target.value.replace(/\D/g, '').substring(0, 4))}
                          placeholder="123"
                          className={errors.cvv ? "border-destructive" : ""}
                        />
                        {errors.cvv && (
                          <p className="text-sm text-destructive mt-1">{errors.cvv}</p>
                        )}
                      </div>
                    </div>

                    <div>
                      <Label htmlFor="cardHolder">Card Holder Name</Label>
                      <Input
                        id="cardHolder"
                        value={paymentData.cardHolder}
                        onChange={(e) => updateField("cardHolder", e.target.value)}
                        placeholder="John Doe"
                        className={errors.cardHolder ? "border-destructive" : ""}
                      />
                      {errors.cardHolder && (
                        <p className="text-sm text-destructive mt-1">{errors.cardHolder}</p>
                      )}
                    </div>
                  </div>

                  {/* Billing Address */}
                  <div className="space-y-4">
                    <h3>Billing Address</h3>
                    
                    <div>
                      <Label htmlFor="billingAddress">Street Address</Label>
                      <Input
                        id="billingAddress"
                        value={paymentData.billingAddress}
                        onChange={(e) => updateField("billingAddress", e.target.value)}
                        placeholder="123 Main St"
                        className={errors.billingAddress ? "border-destructive" : ""}
                      />
                      {errors.billingAddress && (
                        <p className="text-sm text-destructive mt-1">{errors.billingAddress}</p>
                      )}
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <Label htmlFor="city">City</Label>
                        <Input
                          id="city"
                          value={paymentData.city}
                          onChange={(e) => updateField("city", e.target.value)}
                          placeholder="New York"
                          className={errors.city ? "border-destructive" : ""}
                        />
                        {errors.city && (
                          <p className="text-sm text-destructive mt-1">{errors.city}</p>
                        )}
                      </div>
                      <div>
                        <Label htmlFor="zipCode">ZIP Code</Label>
                        <Input
                          id="zipCode"
                          value={paymentData.zipCode}
                          onChange={(e) => updateField("zipCode", e.target.value)}
                          placeholder="10001"
                          className={errors.zipCode ? "border-destructive" : ""}
                        />
                        {errors.zipCode && (
                          <p className="text-sm text-destructive mt-1">{errors.zipCode}</p>
                        )}
                      </div>
                    </div>
                  </div>

                  {/* Security Notice */}
                  <div className="bg-blue-50 p-4 rounded-lg border border-blue-200">
                    <div className="flex items-center gap-2 text-blue-800">
                      <Shield className="w-4 h-4" />
                      <span className="font-medium">Secure Payment</span>
                    </div>
                    <p className="text-sm text-blue-700 mt-1">
                      Your payment is protected by 256-bit SSL encryption. We never store your card details.
                    </p>
                  </div>

                  {/* Submit Button */}
                  <Button 
                    type="submit" 
                    size="lg" 
                    className="w-full"
                    disabled={isProcessing}
                  >
                    {isProcessing ? (
                      <>Processing Payment...</>
                    ) : (
                      <>Complete Payment - ${amount.toLocaleString()}</>
                    )}
                  </Button>
                </form>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}