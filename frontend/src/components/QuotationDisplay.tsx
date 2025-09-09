import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Badge } from "./ui/badge";
import { ArrowLeft, CheckCircle, DollarSign, Calendar } from "lucide-react";
import type { CustomerData } from "./CustomerForm";

export interface Quotation {
  coverageAmount: number;
  insuranceType: string;
  monthlyPremium: number;
  policyId: string;
  premiumTermMonths: number;
  termsAndConditions: string;
}

interface QuotationDisplayProps {
  customerData: CustomerData;
  quotation: any;
  onApply: () => void;
  onBack: () => void;
}

export function QuotationDisplay({ customerData, quotation, onApply, onBack }: QuotationDisplayProps) {
  const getInsuranceTitle = () => {
    switch (quotation.insuranceType) {
      case "life": return "Life Insurance";
      case "health": return "Health Insurance";
      case "vehicle": return "Vehicle Insurance";
      default: return "Insurance";
    }
  };

  // Optionally, you can keep getCoverageDetails if you want to show static features per insuranceType
  const getCoverageDetails = () => {
    switch (quotation.insuranceType) {
      case "life":
        return [
          "Death benefit payout",
          "Accidental death coverage",
          "Terminal illness benefit",
          "Flexible premium payments"
        ];
      case "health":
        return [
          "Medical expense coverage",
          "Prescription drug coverage",
          "Preventive care",
          "Emergency services"
        ];
      case "vehicle":
        return [
          "Collision damage coverage",
          "Comprehensive protection",
          "Liability coverage",
          "24/7 roadside assistance"
        ];
      default:
        return [];
    }
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
          Back to Form
        </Button>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Customer Summary */}
          <div className="lg:col-span-1">
            <Card>
              <CardHeader>
                <CardTitle>Application Summary</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <p className="text-sm text-muted-foreground">Name</p>
                  <p className="font-medium">{customerData.customerName}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Age</p>
                  <p className="font-medium">{customerData.customerAge} years</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Gender</p>
                  <p className="font-medium capitalize">{customerData.customerGender}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Annual Income</p>
                  <p className="font-medium">${parseInt(customerData.customerIncome).toLocaleString()}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Occupation</p>
                  <p className="font-medium">{customerData.customerOccupation}</p>
                </div>
                {customerData.vehicleType && (
                  <div>
                    <p className="text-sm text-muted-foreground">Vehicle Type</p>
                    <p className="font-medium capitalize">{customerData.vehicleType}</p>
                  </div>
                )}
              </CardContent>
            </Card>
          </div>

          {/* Quote Details */}
          <div className="lg:col-span-2">
            <Card>
              <CardHeader>
                <div className="flex items-center justify-between">
                  <CardTitle>{getInsuranceTitle()} Quote</CardTitle>
                  <Badge variant="outline" className="bg-green-50 text-green-700 border-green-200">
                    <CheckCircle className="w-3 h-3 mr-1" />
                    Approved
                  </Badge>
                </div>
                <p className="text-sm text-muted-foreground">Policy ID: {quotation.policyId}</p>
              </CardHeader>
              <CardContent className="space-y-6">
                {/* Pricing */}
                <div className="grid grid-cols-2 gap-4">
                  <div className="bg-primary/5 p-4 rounded-lg">
                    <div className="flex items-center gap-2 mb-2">
                      <DollarSign className="w-4 h-4 text-primary" />
                      <p className="text-sm text-muted-foreground">Monthly Premium</p>
                    </div>
                    <p className="text-2xl font-bold text-primary">
                      ${quotation.monthlyPremium}
                    </p>
                  </div>
                  <div className="bg-secondary/50 p-4 rounded-lg">
                    <div className="flex items-center gap-2 mb-2">
                      <Calendar className="w-4 h-4 text-secondary-foreground" />
                      <p className="text-sm text-muted-foreground">Premium Term</p>
                    </div>
                    <p className="text-2xl font-bold">
                      {quotation.premiumTermMonths} months
                    </p>
                  </div>
                </div>

                {/* Coverage Details */}
                <div>
                  <p className="text-sm text-muted-foreground mb-1">Coverage Amount</p>
                  <p className="font-bold text-lg">${quotation.coverageAmount.toLocaleString()}</p>
                </div>

                {/* Coverage Features */}
                <div>
                  <h3 className="mb-3">What's Covered</h3>
                  <div className="grid grid-cols-2 gap-2">
                    {getCoverageDetails().map((feature, index) => (
                      <div key={index} className="flex items-center gap-2">
                        <CheckCircle className="w-4 h-4 text-green-500 flex-shrink-0" />
                        <span className="text-sm">{feature}</span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Terms and Conditions */}
                <div className="bg-amber-50 p-4 rounded-lg border border-amber-200">
                  <p className="text-sm text-amber-800">
                    <strong>Terms & Conditions:</strong> {quotation.termsAndConditions}
                  </p>
                </div>

                {/* Action Button */}
                <Button onClick={onApply} size="lg" className="w-full">
                  Apply for This Insurance
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}