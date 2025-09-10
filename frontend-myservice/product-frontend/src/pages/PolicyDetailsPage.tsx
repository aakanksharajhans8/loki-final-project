// src/pages/PolicyDetailsPage.tsx
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import  { getPolicyByNumber } from '../api/apiservices';
import type { Policy } from '../types/insurance';

function PolicyDetailsPage() {
    // Get the 'policyNumber' from the URL
    const { policyNumber } = useParams<{ policyNumber: string }>();
    const [policy, setPolicy] = useState<Policy | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (policyNumber) {
            const fetchPolicy = async () => {
                try {
                    const data = await getPolicyByNumber(policyNumber);
                    setPolicy(data);
                } catch (err) {
                    setError('Failed to fetch policy details. Please check the policy number and try again.');
                } finally {
                    setLoading(false);
                }
            };
            fetchPolicy();
        }
    }, [policyNumber]);

    if (loading) return <div>Loading policy details...</div>;
    if (error) return <div className="error-message">{error}</div>;
    if (!policy) return <div>No policy found.</div>;

    return (
        <div className="card">
            <h2>Policy Details</h2>
            <p><strong>Policy Number:</strong> {policy.policyNumber}</p>
            <p><strong>Customer ID:</strong> {policy.customerId}</p>
            <p><strong>Status:</strong> <span className={`status status-${policy.status?.toLowerCase()}`}>{policy.status}</span></p>
            <p><strong>Premium:</strong> ${policy.premiumAmount.toLocaleString()}</p>
            <p><strong>Effective Period:</strong> {new Date(policy.startDate).toLocaleDateString()} - {new Date(policy.endDate).toLocaleDateString()}</p>

            <div className="endorsements-section">
                <h3>Endorsements ({policy.endorsements.length})</h3>
                {policy.endorsements.length > 0 ? (
                    <ul>
                        {policy.endorsements.map(e => (
                            <li key={e.id}>
                                <strong>{e.description}</strong> (Effective: {new Date(e.effectiveDate).toLocaleDateString()})
                                <p>{e.changeDetails}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No endorsements for this policy.</p>
                )}
            </div>
        </div>
    );
}

export default PolicyDetailsPage;