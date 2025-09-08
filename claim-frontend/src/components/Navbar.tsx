import { Link, useLocation } from "react-router-dom"

export default function Navbar() {
  const location = useLocation()

  const navItems = [
    { name: "Policy", path: "/" },
    { name: "Claim Apply", path: "/claim" },
    { name: "Claim Status", path: "/settled" },
  ]

  return (
    <nav className="bg-white shadow-md fixed top-0 left-0 w-full z-50">
      <div className="w-full px-6 py-3 flex items-center justify-between">
       
        <Link to="/" className="text-xl font-bold text-blue-700 hover:text-blue-900 transition">
          Insurance Portal
        </Link>

        {/* Nav Items */}
        <div className="flex space-x-6">
          {navItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`px-4 py-2 rounded-lg font-medium transition ${
                location.pathname === item.path
                  ? "bg-blue-600 text-white"
                  : "text-gray-700 hover:bg-blue-100"
              }`}
            >
              {item.name}
            </Link>
          ))}
        </div>
      </div>
    </nav>
  )
}
