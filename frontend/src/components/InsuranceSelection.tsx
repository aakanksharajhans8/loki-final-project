import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Button } from "./ui/button";
import { Shield, Heart, Car } from "lucide-react";
import { ImageWithFallback } from "../assets/ImageWithFallback";

interface InsuranceSelectionProps {
  onSelect: (insuranceType: string) => void;
}

export function InsuranceSelection({ onSelect }: InsuranceSelectionProps) {
  const insuranceTypes = [
    {
      type: "life",
      title: "Life Insurance",
      description: "Protect your family's financial future with comprehensive life insurance coverage. Get peace of mind knowing your loved ones will be financially secure.",
      icon: Heart,
      benefits: ["Death benefit protection", "Cash value accumulation", "Tax advantages", "Flexible premium options"]
    },
    {
      type: "health",
      title: "Health Insurance",
      description: "Stay healthy and protected with our comprehensive health insurance plans. Coverage for medical expenses, preventive care, and more.",
      icon: Shield,
      benefits: ["Medical expense coverage", "Preventive care", "Prescription drugs", "Emergency services"]
    },
    {
      type: "vehicle",
      title: "Vehicle Insurance",
      description: "Drive with confidence knowing your vehicle is protected. Comprehensive coverage for accidents, theft, and damage.",
      icon: Car,
      benefits: ["Collision coverage", "Comprehensive protection", "Liability coverage", "24/7 roadside assistance"]
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12">
      <div className="container mx-auto px-4">
        {/* Header */}
        <div className="text-center mb-12">
          <div className="mb-8">
            <ImageWithFallback
              src="https://images.unsplash.com/photo-1659352786973-82ae3af461a3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxpbnN1cmFuY2UlMjBwcm90ZWN0aW9uJTIwZmFtaWx5fGVufDF8fHx8MTc1NzIzMjkyMXww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
              alt="Insurance Protection"
              className="w-full h-64 object-cover rounded-2xl shadow-lg"
            />
          </div>
          <h1 className="mb-4 text-primary">SecureLife Insurance</h1>
          <p className="max-w-2xl mx-auto text-muted-foreground">
            Choose the right insurance plan for your needs. We offer comprehensive coverage 
            to protect what matters most to you and your family.
          </p>
        </div>

        {/* Insurance Options */}
        <div className="grid md:grid-cols-3 gap-8">
          {insuranceTypes.map((insurance) => {
            const IconComponent = insurance.icon;
            return (
              <Card key={insurance.type} className="hover:shadow-lg transition-shadow duration-300">
                <CardHeader className="text-center">
                  <div className="w-16 h-16 mx-auto mb-4 bg-primary/10 rounded-full flex items-center justify-center">
                    <IconComponent className="w-8 h-8 text-primary" />
                  </div>
                  <CardTitle>{insurance.title}</CardTitle>
                  <CardDescription className="mt-2">
                    {insurance.description}
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <ul className="space-y-2 mb-6">
                    {insurance.benefits.map((benefit, index) => (
                      <li key={index} className="flex items-center gap-2 text-sm text-muted-foreground">
                        <div className="w-1.5 h-1.5 bg-primary rounded-full" />
                        {benefit}
                      </li>
                    ))}
                  </ul>
                  <Button 
                    className="w-full" 
                    onClick={() => onSelect(insurance.type)}
                  >
                    Get Quote
                  </Button>
                </CardContent>
              </Card>
            );
          })}
        </div>
      </div>
    </div>
  );
}