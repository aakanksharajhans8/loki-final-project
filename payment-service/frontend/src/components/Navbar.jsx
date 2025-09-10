import { Link, useLocation } from "react-router-dom";
import { useState } from "react";
import { FaBars, FaTimes } from "react-icons/fa";

export default function Navbar() {
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(false);

  const navItems = [
  { path: "/", label: "Home" },
  { path: "/pay", label: "Pay Premium" },
  { path: "/transactions", label: "Transactions" },
  { path: "/contact", label: "Contact Us" },
];

  return (
    <nav className="fixed top-0 left-0 w-full bg-white shadow-lg z-50 backdrop-blur-md bg-opacity-80 border-b border-gray-200 transition-colors duration-300">
      <div className="max-w-6xl mx-auto flex justify-between items-center px-6 py-4">
        {/* Logo + Brand */}
        <Link
          to="/"
          className="flex items-center gap-2 text-2xl font-bold text-blue-700 hover:text-blue-900 transition"
          aria-label="InsurePay Home"
        >
          <span role="img" aria-label="shield" className="text-blue-600">
            🛡️
          </span>{" "}
          InsurePay
        </Link>

        {/* Desktop Menu */}
        <div className="hidden md:flex space-x-8">
          {navItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`transition font-medium py-2 border-b-2 ${
                location.pathname === item.path
                  ? "text-blue-700 border-blue-700"
                  : "text-gray-700 border-transparent hover:text-blue-600 hover:border-blue-600"
              }`}
            >
              {item.label}
            </Link>
          ))}
        </div>

        {/* Mobile Menu Button */}
        <button
          className="md:hidden text-2xl text-gray-700 hover:text-blue-600"
          onClick={() => setIsOpen(!isOpen)}
          aria-label={isOpen ? "Close menu" : "Open menu"}
        >
          {isOpen ? <FaTimes /> : <FaBars />}
        </button>
      </div>

      {/* Mobile Dropdown */}
      {isOpen && (
        <div className="md:hidden bg-white shadow-lg border-t border-gray-200">
          <div className="flex flex-col px-6 py-4 space-y-4">
            {navItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                onClick={() => setIsOpen(false)}
                className={`transition font-medium py-2 ${
                  location.pathname === item.path
                    ? "text-blue-700"
                    : "text-gray-700 hover:text-blue-600"
                }`}
              >
                {item.label}
              </Link>
            ))}
          </div>
        </div>
      )}
    </nav>
  );
}
