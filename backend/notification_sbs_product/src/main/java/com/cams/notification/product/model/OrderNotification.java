package com.cams.notification.product.model;
public class OrderNotification {
  private Long id; private String customerName; private String email; private String orderId; private String productName; private int quantity; private double price; private double total;
  public OrderNotification(){}
  public OrderNotification(String customerName,String email,String orderId,String productName,int quantity,double price,double total){
    this.customerName=customerName; this.email=email; this.orderId=orderId; this.productName=productName; this.quantity=quantity; this.price=price; this.total=total;
  }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getOrderId(){return orderId;} public void setOrderId(String v){orderId=v;}
  public String getProductName(){return productName;} public void setProductName(String v){productName=v;}
  public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
  public double getPrice(){return price;} public void setPrice(double v){price=v;}
  public double getTotal(){return total;} public void setTotal(double v){total=v;}
}
