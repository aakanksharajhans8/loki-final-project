import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select";
import { ArrowLeft } from "lucide-react";

export  interface CustomerData {
  customerName: string;
  customerAge: string;
  customerGender: string;
  customerIncome: string;
  customerOccupation: string;
  insuranceType: string;
  vehicleType?: string;
}

interface CustomerFormProps {
  insuranceType: string;
  onSubmit: (data: CustomerData) => void;
  onBack: () => void;
}

export default function CustomerForm({ insuranceType, onSubmit, onBack }: CustomerFormProps) {
  const [formData, setFormData] = useState<CustomerData>({
    customerName: "",
    customerAge: "",
    customerGender: "",
    customerIncome: "",
    customerOccupation: "",
    insuranceType,
    vehicleType: ""
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.customerName.trim()) {
      newErrors.customerName = "Name is required";
    }

    if (!formData.customerAge || parseInt(formData.customerAge) < 18 || parseInt(formData.customerAge) > 100) {
      newErrors.customerAge = "Age must be between 18 and 100";
    }

    if (!formData.customerGender) {
      newErrors.customerGender = "Gender is required";
    }

    if (!formData.customerIncome || parseInt(formData.customerIncome) < 0) {
      newErrors.customerIncome = "Valid income is required";
    }

    if (!formData.customerOccupation.trim()) {
      newErrors.customerOccupation = "Occupation is required";
    }

    if (insuranceType === "vehicle" && !formData.vehicleType) {
      newErrors.vehicleType = "Vehicle type is required for vehicle insurance";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  const updateField = (field: keyof CustomerData, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: "" }));
    }
  };

  const getInsuranceTitle = () => {
    switch (insuranceType) {
      case "life": return "Life Insurance";
      case "health": return "Health Insurance";
      case "vehicle": return "Vehicle Insurance";
      default: return "Insurance";
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12">
      <div className="container mx-auto px-4 max-w-2xl">
        <Button
          variant="ghost"
          onClick={onBack}
          className="mb-6"
        >
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Insurance Options
        </Button>

        <Card>
          <CardHeader>
            <CardTitle className="text-center">
              {getInsuranceTitle()} Application
            </CardTitle>
            <p className="text-center text-muted-foreground">
              Please fill out the form below to get your personalized quote
            </p>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div>
                <Label htmlFor="customerName">Full Name</Label>
                <Input
                  id="customerName"
                  value={formData.customerName}
                  onChange={(e) => updateField("customerName", e.target.value)}
                  placeholder="Enter your full name"
                  className={errors.customerName ? "border-destructive" : ""}
                />
                {errors.customerName && (
                  <p className="text-sm text-destructive mt-1">{errors.customerName}</p>
                )}
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="customerAge">Age</Label>
                  <Input
                    id="customerAge"
                    type="number"
                    value={formData.customerAge}
                    onChange={(e) => updateField("customerAge", e.target.value)}
                    placeholder="Enter your age"
                    min="18"
                    max="100"
                    className={errors.customerAge ? "border-destructive" : ""}
                  />
                  {errors.customerAge && (
                    <p className="text-sm text-destructive mt-1">{errors.customerAge}</p>
                  )}
                </div>

                <div>
                  <Label htmlFor="customerGender">Gender</Label>
                  <Select value={formData.customerGender} onValueChange={(value) => updateField("customerGender", value)}>
                    <SelectTrigger className={errors.customerGender ? "border-destructive" : ""}>
                      <SelectValue placeholder="Select gender" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="male">Male</SelectItem>
                      <SelectItem value="female">Female</SelectItem>
                      <SelectItem value="other">Other</SelectItem>
                    </SelectContent>
                  </Select>
                  {errors.customerGender && (
                    <p className="text-sm text-destructive mt-1">{errors.customerGender}</p>
                  )}
                </div>
              </div>

              <div>
                <Label htmlFor="customerIncome">Annual Income ($)</Label>
                <Input
                  id="customerIncome"
                  type="number"
                  value={formData.customerIncome}
                  onChange={(e) => updateField("customerIncome", e.target.value)}
                  placeholder="Enter your annual income"
                  min="0"
                  className={errors.customerIncome ? "border-destructive" : ""}
                />
                {errors.customerIncome && (
                  <p className="text-sm text-destructive mt-1">{errors.customerIncome}</p>
                )}
              </div>

              <div>
                <Label htmlFor="customerOccupation">Occupation</Label>
                <Input
                  id="customerOccupation"
                  value={formData.customerOccupation}
                  onChange={(e) => updateField("customerOccupation", e.target.value)}
                  placeholder="Enter your occupation"
                  className={errors.customerOccupation ? "border-destructive" : ""}
                />
                {errors.customerOccupation && (
                  <p className="text-sm text-destructive mt-1">{errors.customerOccupation}</p>
                )}
              </div>

              {insuranceType === "vehicle" && (
                <div>
                  <Label htmlFor="vehicleType">Vehicle Type</Label>
                  <Select value={formData.vehicleType} onValueChange={(value) => updateField("vehicleType", value)}>
                    <SelectTrigger className={errors.vehicleType ? "border-destructive" : ""}>
                      <SelectValue placeholder="Select vehicle type" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="car">Car</SelectItem>
                      <SelectItem value="motorcycle">Motorcycle</SelectItem>
                      <SelectItem value="truck">Truck</SelectItem>
                      <SelectItem value="suv">SUV</SelectItem>
                      <SelectItem value="van">Van</SelectItem>
                    </SelectContent>
                  </Select>
                  {errors.vehicleType && (
                    <p className="text-sm text-destructive mt-1">{errors.vehicleType}</p>
                  )}
                </div>
              )}

              <Button type="submit" className="w-full">
                Get My Quote
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}