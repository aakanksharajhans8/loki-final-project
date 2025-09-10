import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function HomePage() {
    const [policyNumber, setPolicyNumber] = useState('');
    const navigate = useNavigate();

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        if (policyNumber.trim()) {
            navigate(`/policy/${policyNumber.trim()}`);
        }
    };

    return (
        <div>
            <h1>Insurance Policy Management</h1>
            <p>Welcome to your insurance dashboard. View our products or search for an existing policy.</p>
            <form onSubmit={handleSearch} className="policy-search-form">
                <input
                    type="text"
                    value={policyNumber}
                    onChange={(e) => setPolicyNumber(e.target.value)}
                    placeholder="Enter Policy Number"
                    className="search-input"
                />
                <button type="submit" className="search-button">Search Policy</button>
            </form>
        </div>
    );
}

export default HomePage;