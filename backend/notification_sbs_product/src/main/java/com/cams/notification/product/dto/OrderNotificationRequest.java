package com.cams.notification.product.dto;

import jakarta.validation.constraints.*;

public class OrderNotificationRequest {

    @NotBlank(message = "customerName is required")
    @Size(min = 2, max = 100, message = "customerName must be 2-100 characters")
    private String customerName;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "email must not contain spaces")
    private String email;

    @NotBlank(message = "orderId is required")
    @Pattern(regexp = "^[A-Za-z0-9-]{3,40}$", message = "orderId must be 3-40 chars (letters, digits, hyphen)")
    private String orderId;

    @NotBlank(message = "productName is required")
    @Size(min = 2, max = 255, message = "productName must be 2-255 characters")
    private String productName;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    @DecimalMin(value = "0.01", inclusive = true, message = "price must be >= 0.01")
    private double price;

    // getters/setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { this.customerName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String v) { this.orderId = v; }
    public String getProductName() { return productName; }
    public void setProductName(String v) { this.productName = v; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int v) { this.quantity = v; }
    public double getPrice() { return price; }
    public void setPrice(double v) { this.price = v; }
}
