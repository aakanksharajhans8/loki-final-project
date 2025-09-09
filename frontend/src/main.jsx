import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import "./style.css";

function App() {
  const [customers, setCustomers] = useState([]);
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    dob: ""
  });

  // Load all customers
  const fetchCustomers = () => {
    fetch("http://localhost:8080/api/customers")
      .then((res) => res.json())
      .then((data) => setCustomers(data))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  // Handle form submit
  const handleSubmit = (e) => {
    e.preventDefault();
    fetch("http://localhost:8080/api/customers", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form)
    })
      .then(() => {
        setForm({ firstName: "", lastName: "", email: "", phone: "", dob: "" });
        fetchCustomers(); // reload list
      })
      .catch((err) => console.error(err));
  };

  // Toggle KYC
  const toggleKyc = (uuid, current) => {
    fetch(`http://localhost:8080/api/customers/${uuid}/kyc?verified=${!current}`, {
      method: "POST"
    })
      .then(() => fetchCustomers())
      .catch((err) => console.error(err));
  };

  return (
    <div style={{ padding: "20px", fontFamily: "sans-serif" }}>
      <h1>Customer Service UI</h1>

      {/* Create Form */}
      <h2>Add Customer</h2>
      <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
        <input
          placeholder="First Name"
          value={form.firstName}
          onChange={(e) => setForm({ ...form, firstName: e.target.value })}
          required
        />
        <input
          placeholder="Last Name"
          value={form.lastName}
          onChange={(e) => setForm({ ...form, lastName: e.target.value })}
          required
        />
        <input
          placeholder="Email"
          value={form.email}
          onChange={(e) => setForm({ ...form, email: e.target.value })}
          required
        />
        <input
          placeholder="Phone"
          value={form.phone}
          onChange={(e) => setForm({ ...form, phone: e.target.value })}
        />
        <input
          type="date"
          value={form.dob}
          onChange={(e) => setForm({ ...form, dob: e.target.value })}
        />
        <button type="submit">Add</button>
      </form>

      {/* List Customers */}
      <h2>Customers</h2>
      {customers.length === 0 ? (
        <p>No customers found</p>
      ) : (
        <ul>
          {customers.map((c) => (
            <li key={c.customerUuid}>
              {c.firstName} {c.lastName} — {c.email} — KYC:{" "}
              {c.kycVerified ? "✅" : "❌"}{" "}
              <button onClick={() => toggleKyc(c.customerUuid, c.kycVerified)}>
                Toggle KYC
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

createRoot(document.getElementById("root")).render(<App />);
