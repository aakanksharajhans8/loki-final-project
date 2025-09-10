import React, { useState } from "react";
import { toast } from "react-toastify";
import AuthLayout from "../components/AuthLayout";

const API_URL = "http://localhost:8080/api/auth";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");

  const handleForgotPassword = async (e) => {
    e.preventDefault();

    if (!email) {
      toast.error("Please enter your email.");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/forgot-password`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });

      const data = await response.json();

      if (response.ok) {
        toast.success("Password reset link sent! Check your email.");
      } else {
        toast.error(data.message || "Failed to send reset link.");
      }
    } catch (error) {
      toast.error("An error occurred. Please try again.");
    }
  };

  return (
    <AuthLayout
      title="Forgot Password"
      subtitle="Enter your email to receive a password reset link."
    >
      <form onSubmit={handleForgotPassword} className="space-y-6">
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 transition-all"
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white p-4 rounded-xl shadow-lg font-bold hover:bg-blue-700 transition duration-200"
        >
          Send Reset Link
        </button>
      </form>
    </AuthLayout>
  );
};

export default ForgotPassword;
