import { FaShieldAlt, FaReceipt, FaChartLine, FaHeadset } from "react-icons/fa";

export default function Home() {
  const featureStyle = {
    background: "white",
    borderRadius: "1rem",
    padding: "2rem",
    boxShadow: "0 2px 15px rgba(0,0,0,0.05)",
    border: "1px solid #e5e7eb",
    textAlign: "center",
    transition: "box-shadow 0.3s ease, transform 0.3s ease",
    cursor: "default",
  };
  const featureHover = {
    boxShadow: "0 8px 24px rgba(0,0,0,0.1)",
    transform: "translateY(-5px)",
  };

  return (
    <section style={{ maxWidth: "960px", margin: "4rem auto", padding: "0 1rem" }}>
      <h1 style={{ fontSize: "2.5rem", fontWeight: '700', marginBottom: '1rem', color: '#1f2937', lineHeight: 1.1 }}>
        Secure & Easy <span style={{ color: "#2563eb" }}>Insurance Payments</span>
      </h1>
      <p style={{ fontSize: "1.125rem", color: "#4b5563", marginBottom: "2rem" }}>
        Pay your insurance premium securely and instantly. Keep track of all your payments and receipts in one place.
      </p>

      <div style={{display: "flex", gap: "1rem", justifyContent: "space-between", flexWrap: "wrap"}}>
        <div
          style={featureStyle}
          onMouseEnter={e => {
            e.currentTarget.style.boxShadow = featureHover.boxShadow;
            e.currentTarget.style.transform = featureHover.transform;
          }}
          onMouseLeave={e => {
            e.currentTarget.style.boxShadow = featureStyle.boxShadow;
            e.currentTarget.style.transform = "none";
          }}
        >
          <FaShieldAlt color="#2563eb" size={48} style={{ marginBottom: "1rem" }} />
          <h3 style={{ marginBottom: "0.5rem" }}>Secure Payments</h3>
          <p style={{ color: "#4b5563", fontSize: "0.875rem" }}>
            Transactions are encrypted and verified for complete security.
          </p>
        </div>

        <div
          style={featureStyle}
          onMouseEnter={e => {
            e.currentTarget.style.boxShadow = featureHover.boxShadow;
            e.currentTarget.style.transform = featureHover.transform;
          }}
          onMouseLeave={e => {
            e.currentTarget.style.boxShadow = featureStyle.boxShadow;
            e.currentTarget.style.transform = "none";
          }}
        >
          <FaReceipt color="#16a34a" size={48} style={{ marginBottom: "1rem" }} />
          <h3 style={{ marginBottom: "0.5rem" }}>Instant Receipts</h3>
          <p style={{ color: "#4b5563", fontSize: "0.875rem" }}>
            Get digital receipts instantly for every payment you make.
          </p>
        </div>

        <div
          style={featureStyle}
          onMouseEnter={e => {
            e.currentTarget.style.boxShadow = featureHover.boxShadow;
            e.currentTarget.style.transform = featureHover.transform;
          }}
          onMouseLeave={e => {
            e.currentTarget.style.boxShadow = featureStyle.boxShadow;
            e.currentTarget.style.transform = "none";
          }}
        >
          <FaChartLine color="#8b5cf6" size={48} style={{ marginBottom: "1rem" }} />
          <h3 style={{ marginBottom: "0.5rem" }}>Easy Tracking</h3>
          <p style={{ color: "#4b5563", fontSize: "0.875rem" }}>
            Monitor your payment history and policies in one place.
          </p>
        </div>

        <div
          style={featureStyle}
          onMouseEnter={e => {
            e.currentTarget.style.boxShadow = featureHover.boxShadow;
            e.currentTarget.style.transform = featureHover.transform;
          }}
          onMouseLeave={e => {
            e.currentTarget.style.boxShadow = featureStyle.boxShadow;
            e.currentTarget.style.transform = "none";
          }}
        >
          <FaHeadset color="#db2777" size={48} style={{ marginBottom: "1rem" }} />
          <h3 style={{ marginBottom: "0.5rem" }}>24/7 Support</h3>
          <p style={{ color: "#4b5563", fontSize: "0.875rem" }}>
            We are here to help you anytime with dedicated customer support.
          </p>
        </div>
      </div>
    </section>
  );
}
