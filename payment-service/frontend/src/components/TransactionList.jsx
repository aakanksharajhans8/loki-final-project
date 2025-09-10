import { useEffect, useState } from "react";
import { getTransactions, getReceipt } from "../api/paymentApi";
import { FaDownload, FaCheckCircle, FaTimesCircle } from "react-icons/fa";

export default function TransactionList() {
  const [transactions, setTransactions] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    (async () => {
      try {
        const res = await getTransactions();
        const formatted = res.data.map((t) => ({
          id: t.id,
          policyNumber: t.policyNumber || "N/A",
          amount: t.amountPaise ? t.amountPaise / 100 : 0,
          date: t.createdAt,
          status: t.status,
        }));
        setTransactions(formatted);
      } catch {
        alert("Failed to fetch transactions");
      }
    })();
  }, []);

  const formatDate = (dateStr) => {
    if (!dateStr) return "N/A";
    const d = new Date(dateStr);
    if (isNaN(d)) return "N/A";
    return d.toLocaleString();
  };

  const handleReceiptDownload = async (id) => {
    try {
      const response = await getReceipt(id);
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `receipt_${id}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);
      window.URL.revokeObjectURL(url);
    } catch (error) {
      alert("Failed to download receipt: " + error.message);
    }
  };

  const filteredTransactions = transactions.filter(t =>
    t.policyNumber.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <>
      <div style={{ marginBottom: "1rem" }}>
        <input
          type="text"
          placeholder="Search by Policy Number"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{
            padding: "0.5rem",
            marginRight: "0.5rem",
            borderRadius: "0.25rem",
            border: "1px solid #ccc",
          }}
        />
        <button
          onClick={() => {}}
          style={{
            padding: "0.5rem 1rem",
            cursor: "pointer",
            borderRadius: "0.25rem",
            border: "none",
            backgroundColor: "#2563eb",
            color: "white",
          }}
        >
          Search
        </button>
      </div>

      {filteredTransactions.length === 0 ? (
        <div style={{ textAlign: "center", color: "#6b7280", padding: "3rem 0" }}>
          <span style={{ fontSize: "4rem", display: "block", marginBottom: "1rem" }}>📄</span>
          <p>No transactions found.</p>
        </div>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <table>
            <thead>
              <tr>
                <th>Policy</th>
                <th>Amount (₹)</th>
                <th>Date</th>
                <th>Status</th>
                <th>Receipt</th>
              </tr>
            </thead>
            <tbody>
              {filteredTransactions.map((t, i) => (
                <tr
                  key={t.id}
                  style={{
                    backgroundColor: i % 2 === 0 ? "white" : "#f9fafb",
                    transition: "background-color 0.3s",
                  }}
                  onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#e0f2fe")}
                  onMouseLeave={(e) =>
                    (e.currentTarget.style.backgroundColor = i % 2 === 0 ? "white" : "#f9fafb")
                  }
                >
                  <td style={{ padding: "0.75rem" }}>{t.policyNumber}</td>
                  <td style={{ padding: "0.75rem" }}>{t.amount.toFixed(2)}</td>
                  <td style={{ padding: "0.75rem" }}>{formatDate(t.date)}</td>
                  <td style={{ padding: "0.75rem" }}>
                    <span
                      style={{
                        display: "inline-flex",
                        alignItems: "center",
                        gap: "0.5rem",
                        padding: "0.2rem 0.75rem",
                        borderRadius: "9999px",
                        fontSize: "0.75rem",
                        fontWeight: "600",
                        color:
                          t.status === "PAID" || t.status === "SUCCESS" ? "#15803d" : "#b91c1c",
                        backgroundColor:
                          t.status === "PAID" || t.status === "SUCCESS" ? "#d1fae5" : "#fee2e2",
                      }}
                    >
                      {t.status === "PAID" || t.status === "SUCCESS" ? (
                        <FaCheckCircle color="#15803d" />
                      ) : (
                        <FaTimesCircle color="#b91c1c" />
                      )}
                      {t.status}
                    </span>
                  </td>
                  <td style={{ padding: "0.75rem" }}>
                    <button
                      onClick={() => handleReceiptDownload(t.id)}
                      style={{
                        display: "inline-flex",
                        alignItems: "center",
                        gap: "0.5rem",
                        padding: "0.4rem 1rem",
                        fontSize: "0.75rem",
                        borderRadius: "0.5rem",
                        backgroundColor: "#2563eb",
                        color: "white",
                        border: "none",
                        cursor: "pointer",
                      }}
                    >
                      <FaDownload /> Download
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </>
  );
}
