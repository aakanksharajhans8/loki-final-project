package com.insurance.product_policy_service.service;

import com.insurance.product_policy_service.entity.Product;
import com.insurance.product_policy_service.exception.ResourceNotFoundException;
import com.insurance.product_policy_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        // If the product has coverages, we need to set the back-reference
        if (product.getCoverages() != null) {
            product.getCoverages().forEach(coverage -> coverage.setProduct(product));
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product getProductByCode(String productCode) {
        return productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + productCode));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setProductType(productDetails.getProductType());
        existingProduct.setActive(productDetails.isActive());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        // Note: Managing coverages update would require more complex logic
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
