import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ProductListPage from './pages/ProductListPage';
import PolicyDetailsPage from './pages/PolicyDetailsPage';
import HomePage from './pages/HomePage';

function App() {
    return (
        <Router>
            <nav className="navbar">
                <Link to="/">Home</Link>
                <Link to="/products">Insurance Products</Link>
            </nav>
            <main className="container">
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/products" element={<ProductListPage />} />
                    {/* Dynamic route for policy details */}
                    <Route path="/policy/:policyNumber" element={<PolicyDetailsPage />} />
                </Routes>
            </main>
        </Router>
    );
}

export default App;