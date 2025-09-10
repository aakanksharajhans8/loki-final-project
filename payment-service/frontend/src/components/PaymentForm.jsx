import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createOrder, verifyPayment } from "../api/paymentApi";

const policyTypes = [
  "Life Insurance",
  "Health Insurance",
  "Vehicle Insurance",
  "Home Insurance",
  "Travel Insurance",
  "Business Insurance",
];

export default function PaymentForm() {
  const [form, setForm] = useState({
    name: "",
    phone: "",
    email: "",
    policyType: "",
    policyNumber: "",
    amount: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const loadRazorpay = (order) => { 
    const options = {
      key: order.key,
      amount: order.amount,
      currency: order.currency,
      name: "Insurance Payment",
      description: "Insurance Premium Payment",
      order_id: order.orderId,
      handler: async (response) => {
        try {
          await verifyPayment({
            razorpay_payment_id: response.razorpay_payment_id,
            razorpay_order_id: response.razorpay_order_id,
            razorpay_signature: response.razorpay_signature,
          });
          alert("✅ Payment Successful!");
          navigate("/"); // Redirect to Home after success
        } catch {
          setError("❌ Payment verification failed. Please contact support.");
        }
      },
      prefill: {
        name: form.name,
        email: form.email,
        contact: form.phone,
      },
      theme: { color: "#2563eb" },
    };
    const rzp = new window.Razorpay(options);
    rzp.open();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const res = await createOrder({
        name: form.name,
        phone: form.phone,
        email: form.email,
        policyType: form.policyType,
        policyNumber: form.policyNumber,
        amount: Number(form.amount),
      });
      loadRazorpay(res.data);
    } catch {
      setError("❌ Error creating order. Please try again.");
    }
    setLoading(false);
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "1.25rem",
        width: "100%",
      }}
      autoComplete="off"
    >
      <h3
        style={{
          marginBottom: "0.25rem",
          fontWeight: "700",
          fontSize: "1.5rem",
          color: "#1f2937",
        }}
      >
        Pay Your Premium
      </h3>
      <p style={{ fontSize: "1rem", color: "#555", marginBottom: "0.5rem" }}>
        Enter your details below to securely pay your insurance premium.
      </p>

      {error && (
        <div
          style={{
            marginBottom: "10px",
            padding: "10px",
            borderRadius: "7px",
            backgroundColor: "#fee2e2",
            color: "#b91c1c",
            fontWeight: "600",
            fontSize: "0.97rem",
          }}
        >
          {error}
        </div>
      )}

      <label style={{ fontWeight: "600", color: "#374151" }}>
        Full Name
        <input
          name="name"
          type="text"
          required
          placeholder="John Doe"
          value={form.name}
          onChange={handleChange}
          style={{ marginTop: "0.35rem" }}
        />
      </label>

      <label style={{ fontWeight: "600", color: "#374151" }}>
        <input
  name="phone"
  type="tel"
  required
  pattern="\d{10}"
  title="Please enter a 10-digit phone number"
  placeholder="9876543210"
  value={form.phone}
  onChange={handleChange}
  style={{ marginTop: "0.35rem" }}
/>

<input
  name="email"
  type="email"
  required
  placeholder="john@example.com"
  value={form.email}
  onChange={handleChange}
  style={{ marginTop: "0.35rem" }}
/>

      </label>

      <label style={{ fontWeight: "600", color: "#374151" }}>
        Policy Number
        <input
          name="policyNumber"
          type="text"
          required
          placeholder="e.g. PLIC1234567"
          value={form.policyNumber}
          onChange={handleChange}
          style={{ marginTop: "0.35rem" }}
        />
      </label>

      <label style={{ fontWeight: "600", color: "#374151" }}>
        Premium Amount
        <input
          name="amount"
          type="number"
          min={1}
          required
          placeholder="₹5000"
          value={form.amount}
          onChange={handleChange}
          style={{ marginTop: "0.35rem" }}
        />
      </label>

      <label style={{ fontWeight: "600", color: "#374151" }}>
        Policy Type
        <select
          name="policyType"
          required
          value={form.policyType}
          onChange={handleChange}
          style={{ marginTop: "0.35rem" }}
        >
          <option value="" disabled>
            Select Policy Type
          </option>
          {policyTypes.map((type) => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </select>
      </label>

      <button
        type="submit"
        disabled={loading}
        className="primary"
        style={{
          width: "100%",
          marginTop: "1rem",
          fontWeight: "700",
          fontSize: "1.1rem",
          padding: "0.9rem",
          borderRadius: "1rem",
          background: "linear-gradient(90deg, #2563eb 60%, #1d4ed8 100%)",
          color: "white",
          boxShadow: "0 2px 8px rgba(37,99,235,0.09)",
          letterSpacing: "0.01em",
          cursor: loading ? "not-allowed" : "pointer",
          opacity: loading ? 0.6 : 1,
          transition: "background-color 0.3s ease",
        }}
        onMouseEnter={(e) => {
          if (!loading)
            e.currentTarget.style.background = "linear-gradient(90deg, #1d4ed8 60%, #2563eb 100%)";
        }}
        onMouseLeave={(e) => {
          if (!loading)
            e.currentTarget.style.background = "linear-gradient(90deg, #2563eb 60%, #1d4ed8 100%)";
        }}
      >
        {loading ? "Processing..." : "Pay Now"}
      </button>
    </form>
  );
}
