import TransactionList from "../components/TransactionList";
import { FaHistory } from "react-icons/fa";

export default function Transactions() {
  return (
    <div style={{ maxWidth: "960px", margin: "4rem auto", padding: "0 1rem" }}>
      <div style={{ textAlign: "center", marginBottom: "2rem" }}>
        <FaHistory color="#2563eb" size={64} style={{ marginBottom: "1rem" }} />
        <h2 style={{ fontSize: "2rem", fontWeight: "700", marginBottom: "0.5rem" }}>
          Your Payment History
        </h2>
        <p style={{ color: "#4b5563", fontSize: "1rem", maxWidth: "600px", margin: "0 auto" }}>
          Track all your past premium payments and download receipts anytime.
        </p>
      </div>

      <div className="card">
        <TransactionList />
      </div>
    </div>
  );
}
