import { useState } from "react";
import { BrowserRouter as Router, Routes, Route, NavLink } from "react-router-dom";
import Home from "./pages/Home";
import Pay from "./pages/Pay";
import Transactions from "./pages/Transactions";
import ContactUs from "./pages/ContactUs";


function Navbar() {
  const [mobileOpen, setMobileOpen] = useState(false);

  return (
    <nav>
      <div className="container" style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%'}}>
        <a href="/" style={{fontWeight: '700', fontSize: '1.5rem', color: '#2563eb', textDecoration: 'none'}}>
          🛡️ InsurePay
        </a>

        <button
          className="nav-toggle"
          onClick={() => setMobileOpen(!mobileOpen)}
          aria-label="Toggle navigation"
        >
          ☰
        </button>

        <div className={`nav-links${mobileOpen ? " active" : ""}`}>
  <NavLink to="/" end className={({ isActive }) => (isActive ? "active" : undefined)} onClick={() => setMobileOpen(false)}>
    Home
  </NavLink>
  <NavLink to="/pay" className={({ isActive }) => (isActive ? "active" : undefined)} onClick={() => setMobileOpen(false)}>
    Pay Premium
  </NavLink>
  <NavLink to="/transactions" className={({ isActive }) => (isActive ? "active" : undefined)} onClick={() => setMobileOpen(false)}>
    Transactions
  </NavLink>
  <NavLink to="/contact" className={({ isActive }) => (isActive ? "active" : undefined)} onClick={() => setMobileOpen(false)}>
    Contact Us
  </NavLink>
</div>
    
      </div>
    </nav>
  );
}

export default function App() {
  return (
    <Router>
      <Navbar />
      <main style={{ paddingTop: '80px', flexGrow: 1 }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/pay" element={<Pay />} />
          <Route path="/transactions" element={<Transactions />} />
          <Route path="/contact" element={<ContactUs />} />

        </Routes>
      </main>
    </Router>
  );
}
