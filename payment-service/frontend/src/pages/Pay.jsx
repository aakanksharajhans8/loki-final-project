import PaymentForm from "../components/PaymentForm";
import { FaCreditCard } from "react-icons/fa";

export default function Pay() {
  return (
    <div style={{ maxWidth: "900px", margin: "4rem auto", padding: "0 1rem" }}>
      <div style={{ textAlign: "center", marginBottom: "2.25rem" }}>
        <FaCreditCard color="#2563eb" size={60} style={{ marginBottom: "1rem" }} />
        <h2 style={{
          fontSize: "2.5rem",
          fontWeight: "700",
          color: "#1f2937",
          marginBottom: "0.8rem",
          lineHeight: 1.1,
        }}>
          Pay Your Insurance Premium
        </h2>
        <p style={{ color: "#4b5563", fontSize: "1.125rem", maxWidth: "600px", margin: "0 auto" }}>
          Enter your policy details and securely pay your insurance premium online.<br/>
          Transactions are fast, safe, and fully encrypted.
        </p>
      </div>
      <div className="card" style={{
        margin: "0 auto",
        maxWidth: "480px",
        borderRadius: "2rem",
        boxShadow: "0 8px 32px rgba(37,99,235,0.08)",
        padding: "2.5rem 2rem"
      }}>
        <PaymentForm />
      </div>
    </div>
  );
}
