// src/pages/ProductListPage.tsx
import { useState, useEffect } from 'react';
import { getAllProducts } from '../api/apiservices';
import type { Product } from '../types/insurance';

// Mock data to use if the backend is not ready or has no products yet
const mockProducts: Product[] = [
    { id: 1, productCode: 'PROD-HOME', name: 'Home Insurance', description: 'Comprehensive coverage for your home.', productType: 'Property', active: true, coverages: [], createdAt: new Date().toISOString() },
    { id: 2, productCode: 'PROD-HEALTH', name: 'Health Insurance', description: 'Complete health protection for you and your family.', productType: 'Health', active: true, coverages: [], createdAt: new Date().toISOString() },
    { id: 3, productCode: 'PROD-CAR', name: 'Car Insurance', description: 'Protection for your vehicle against accidents and theft.', productType: 'Auto', active: true, coverages: [], createdAt: new Date().toISOString() },
    { id: 4, productCode: 'PROD-LIFE', name: 'Life Insurance', description: 'Secure your family\'s future.', productType: 'Life', active: true, coverages: [], createdAt: new Date().toISOString() },
];

function ProductListPage() {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await getAllProducts();
                // If backend returns empty, use mock data
                setProducts(data.length > 0 ? data : mockProducts);
            } catch (err) {
                console.error("Failed to fetch products:", err);
                setError('Could not connect to the server. Displaying sample data.');
                setProducts(mockProducts); // Fallback to mock data on error
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    if (loading) return <div>Loading products...</div>;

    return (
        <div>
            <h2>Our Insurance Products</h2>
            {error && <p className="error-message">{error}</p>}
            <div className="product-grid">
                {products.map(product => (
                    <div key={product.id} className="card">
                        <h3>{product.name}</h3>
                        <p>{product.description}</p>
                        <span>Type: {product.productType}</span>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ProductListPage;