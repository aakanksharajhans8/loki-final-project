package com.insurance.product_policy_service.service;
import com.insurance.product_policy_service.entity.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Long id);
    Product getProductByCode(String productCode);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
}
