import { useState } from "react";
import "./SignUp.css";

const Signup = () => {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState({
    username: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: "",
  });

  const [selectedRole, setSelectedRole] = useState("Admin");

  const validateForm = () => {
    let isValid = true;
    const newErrors = {
      username: "",
      email: "",
      phone: "",
      password: "",
      confirmPassword: "",
    };

    // Username check
    if (formData.username.trim() === "") {
      newErrors.username = "Username is required";
      isValid = false;
    }

    // Email check
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      newErrors.email = "Invalid email address";
      isValid = false;
    }

    // Phone number check
    const phoneRegex = /^[0-9]{10}$/; // simple 10-digit validation
    if (!phoneRegex.test(formData.phone)) {
      newErrors.phone = "Enter a valid 10-digit phone number";
      isValid = false;
    }

    // Password check
    if (formData.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters";
      isValid = false;
    }

    // Confirm Password check
    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = "Passwords do not match";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      console.log("Form submitted:", formData, "Role:", selectedRole);
      alert("Signup successful!");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return (
    <div className="signup-container">
      <div className="signup-form">
        <form onSubmit={handleSubmit}>
          <p className="text-4xl font-bold">Create Account</p>

          {/* Username */}
          <label htmlFor="username" className="form-control w-full max-w-xs">
            <div className="label">
              <span className="label-text">Name</span>
            </div>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              placeholder="Type here"
              className="input input-bordered w-full max-w-xs"
              onChange={handleChange}
            />
            <p className="error">{errors.username}</p>
          </label>

          {/* Email */}
          <label htmlFor="email" className="form-control w-full max-w-xs">
            <div className="label">
              <span className="label-text">Email</span>
            </div>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              placeholder="Type here"
              className="input input-bordered w-full max-w-xs"
              onChange={handleChange}
            />
            <p className="error">{errors.email}</p>
          </label>

          {/* Phone Number */}
          <label htmlFor="phone" className="form-control w-full max-w-xs">
            <div className="label">
              <span className="label-text">Phone Number</span>
            </div>
            <input
              type="text"
              id="phone"
              name="phone"
              value={formData.phone}
              placeholder="Enter 10-digit number"
              className="input input-bordered w-full max-w-xs"
              onChange={handleChange}
            />
            <p className="error">{errors.phone}</p>
          </label>

          {/* Password */}
          <label htmlFor="password" className="form-control w-full max-w-xs">
            <div className="label">
              <span className="label-text">Password</span>
            </div>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              placeholder="Type here"
              className="input input-bordered w-full max-w-xs"
              onChange={handleChange}
            />
            <p className="error">{errors.password}</p>
          </label>

          {/* Confirm Password */}
          <label
            htmlFor="confirmPassword"
            className="form-control w-full max-w-xs"
          >
            <div className="label">
              <span className="label-text">Confirm Password</span>
            </div>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              placeholder="Re-enter password"
              className="input input-bordered w-full max-w-xs"
              onChange={handleChange}
            />
            <p className="error">{errors.confirmPassword}</p>
          </label>

          <button
            type="submit"
            className="w-80 bg-cyan-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-cyan-700 transition-colors mt-6 mb-6"
          >
            Create Account
          </button>

          <p className="text-center">
            Already have an account?
            <a href="/login" className="text-purple-700 ml-3">
              Log In
            </a>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Signup;
