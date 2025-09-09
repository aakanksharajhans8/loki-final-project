import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { CheckCircle2, Download, Mail, Calendar, Shield } from "lucide-react";
import type { CustomerData } from "./CustomerForm";
import type { Quotation } from "./QuotationDisplay";

interface SuccessPageProps {
  customerData: CustomerData;
  quotation: Quotation;
  onStartOver: () => void;
}

export function SuccessPage({ customerData, quotation, onStartOver }: SuccessPageProps) {
  const policyNumber = `POL-${Date.now().toString().slice(-6)}-${Math.random().toString(36).substr(2, 4).toUpperCase()}`;
  const effectiveDate = new Date();
  effectiveDate.setDate(effectiveDate.getDate() + 1);
  
  const getInsuranceTitle = () => {
    switch (customerData.insuranceType) {
      case "life": return "Life Insurance";
      case "health": return "Health Insurance";
      case "vehicle": return "Vehicle Insurance";
      default: return "Insurance";
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100 py-12">
      <div className="container mx-auto px-4 max-w-4xl">
        {/* Success Header */}
        <div className="text-center mb-12">
          <div className="w-20 h-20 mx-auto mb-6 bg-green-100 rounded-full flex items-center justify-center">
            <CheckCircle2 className="w-10 h-10 text-green-600" />
          </div>
          <h1 className="mb-4 text-green-800">Payment Successful!</h1>
          <p className="text-lg text-green-700 max-w-2xl mx-auto">
            Congratulations! Your {getInsuranceTitle().toLowerCase()} application has been approved 
            and your policy is now active.
          </p>
        </div>

        <div className="grid lg:grid-cols-2 gap-8">
          {/* Policy Details */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Shield className="w-5 h-5 text-green-600" />
                Policy Information
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="bg-green-50 p-4 rounded-lg border border-green-200">
                <p className="text-sm text-green-800 font-medium">Policy Number</p>
                <p className="text-xl font-bold text-green-900">{policyNumber}</p>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-sm text-muted-foreground">Policy Holder</p>
                  <p className="font-medium">{customerData.customerName}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Insurance Type</p>
                  <p className="font-medium">{getInsuranceTitle()}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Coverage Amount</p>
                  <p className="font-medium">${quotation.coverageAmount.toLocaleString()}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Monthly Premium</p>
                  <p className="font-medium">${quotation.monthlyPremium.toLocaleString()}</p>
                </div>
              </div>

              <div className="border-t pt-4">
                <div className="flex items-center gap-2 mb-2">
                  <Calendar className="w-4 h-4 text-green-600" />
                  <p className="text-sm font-medium text-green-800">Policy Effective Date</p>
                </div>
                <p className="text-lg font-semibold">
                  {effectiveDate.toLocaleDateString('en-US', {
                    weekday: 'long',
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                  })}
                </p>
              </div>

              {customerData.vehicleType && (
                <div>
                  <p className="text-sm text-muted-foreground">Vehicle Type</p>
                  <p className="font-medium capitalize">{customerData.vehicleType}</p>
                </div>
              )}
            </CardContent>
          </Card>

          {/* Next Steps */}
          <Card>
            <CardHeader>
              <CardTitle>What Happens Next?</CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="space-y-4">
                <div className="flex gap-3">
                  <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center flex-shrink-0">
                    <span className="text-sm font-bold text-blue-600">1</span>
                  </div>
                  <div>
                    <p className="font-medium">Policy Documents</p>
                    <p className="text-sm text-muted-foreground">
                      Your policy documents will be emailed to you within 24 hours.
                    </p>
                  </div>
                </div>

                <div className="flex gap-3">
                  <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center flex-shrink-0">
                    <span className="text-sm font-bold text-blue-600">2</span>
                  </div>
                  <div>
                    <p className="font-medium">Coverage Begins</p>
                    <p className="text-sm text-muted-foreground">
                      Your insurance coverage will be active starting {effectiveDate.toLocaleDateString()}.
                    </p>
                  </div>
                </div>

                <div className="flex gap-3">
                  <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center flex-shrink-0">
                    <span className="text-sm font-bold text-blue-600">3</span>
                  </div>
                  <div>
                    <p className="font-medium">Customer Portal Access</p>
                    <p className="text-sm text-muted-foreground">
                      You'll receive login credentials to manage your policy online.
                    </p>
                  </div>
                </div>
              </div>

              <div className="space-y-3">
                <Button className="w-full" variant="outline">
                  <Download className="w-4 h-4 mr-2" />
                  Download Policy Summary
                </Button>

                <Button className="w-full" variant="outline">
                  <Mail className="w-4 h-4 mr-2" />
                  Email Policy Details
                </Button>
              </div>

              <div className="bg-blue-50 p-4 rounded-lg border border-blue-200">
                <p className="text-sm text-blue-800">
                  <strong>Need Help?</strong> Contact our customer service at{' '}
                  <span className="font-medium">1-800-SECURE-1</span> or{' '}
                  <span className="font-medium">support@securelife.com</span>
                </p>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Action Buttons */}
        <div className="text-center mt-12">
          <Button onClick={onStartOver} size="lg" variant="outline">
            Apply for Another Policy
          </Button>
        </div>
      </div>
    </div>
  );
}