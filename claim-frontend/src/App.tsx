import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Navbar from "./components/Navbar"
import PolicyPage from "./pages/PolicyPage"
import ClaimApplyPage from "./pages/ClaimApplyPage"
import SettledClaimPage from "./pages/SettledClaimPage"

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<PolicyPage />} />
        <Route path="/claim" element={<ClaimApplyPage />} />
        <Route path="/settled" element={<SettledClaimPage />} />
      </Routes>
    </Router>
  )
}

export default App
