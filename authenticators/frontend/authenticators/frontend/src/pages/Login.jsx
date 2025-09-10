import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import AuthLayout from "../components/AuthLayout";

const API_URL = "http://localhost:8080/api/auth";

const Login = ({ setAuthUser }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    // Simple validation
    if (!email || !password) {
      toast.error("Email and Password are required.");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (response.ok) {
        toast.success(`Welcome, ${data.firstName}! Logged in successfully.`);
        setAuthUser(data);
        navigate("/profile");
      } else {
        toast.error(data.message || "Invalid credentials.");
      }
    } catch (error) {
      toast.error("An error occurred. Please try again.");
    }
  };

  return (
    <AuthLayout title="Log In" subtitle="Welcome back to your account.">
      <form onSubmit={handleLogin} className="space-y-6">
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 transition-all"
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 transition-all"
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white p-4 rounded-xl shadow-lg font-bold hover:bg-blue-700 transition duration-200"
        >
          Log In
        </button>
      </form>
      <div className="text-center text-sm text-gray-500 space-y-2 mt-4">
        <p>
          Don't have an account?{" "}
          <Link
            to="/signup"
            className="text-blue-600 hover:text-blue-800 font-medium"
          >
            Sign Up
          </Link>
        </p>
        <p>
          <Link
            to="/forgot-password"
            className="text-blue-600 hover:text-blue-800 font-medium"
          >
            Forgot password?
          </Link>
        </p>
      </div>
    </AuthLayout>
  );
};

export default Login;
