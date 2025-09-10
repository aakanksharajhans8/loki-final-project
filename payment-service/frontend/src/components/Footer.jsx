import { FaGithub, FaLinkedin, FaTwitter } from "react-icons/fa";

export default function Footer() {
  return (
    <footer style={{ background: "#111827", color: "#d1d5db", padding: "2rem 1rem", marginTop: "auto" }}>
      <div style={{ maxWidth: "1280px", margin: "0 auto", display: "flex", flexWrap: "wrap", justifyContent: "space-between", alignItems: "center" }}>
        <div>
          <h2 style={{ fontWeight: "700", color: "white", marginBottom: "0.25rem" }}>InsurePay</h2>
          <p style={{ fontSize: "0.875rem", color: "#9ca3af" }}>Secure. Reliable. Hassle-Free Insurance Payments.</p>
        </div>
        <div style={{ display: "flex", gap: "2rem", fontSize: "1.25rem" }}>
          <a href="https://github.com" target="_blank" rel="noopener noreferrer" style={{ color: "#d1d5db" }} aria-label="GitHub">
            <FaGithub />
          </a>
          <a href="https://linkedin.com" target="_blank" rel="noopener noreferrer" style={{ color: "#d1d5db" }} aria-label="LinkedIn">
            <FaLinkedin />
          </a>
          <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" style={{ color: "#d1d5db" }} aria-label="Twitter">
            <FaTwitter />
          </a>
        </div>
      </div>
      <div style={{ borderTop: "1px solid #374151", marginTop: "1.5rem", textAlign: "center", fontSize: "0.75rem", color: "#6b7280", userSelect: "none" }}>
        © {new Date().getFullYear()} InsurePay. All rights reserved.
      </div>
    </footer>
  );
}
