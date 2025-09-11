package com.cams.notification.product.controller;
import com.cams.notification.product.dto.*; import com.cams.notification.product.model.*; import com.cams.notification.product.repo.*; import com.cams.notification.product.service.*;
import jakarta.validation.Valid; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/order") @CrossOrigin(origins="*")
public class OrderController {
  private final MailService mailService; private final OrderNotificationRepository repository;
  public OrderController(MailService mailService, OrderNotificationRepository repository){ this.mailService=mailService; this.repository=repository; }
  @PostMapping("/notify")
  public ResponseEntity<ApiResponse> notifyOrder(@Valid @RequestBody OrderNotificationRequest req){
    double total=req.getPrice()*req.getQuantity();
    OrderNotification entity=new OrderNotification(req.getCustomerName().trim(),req.getEmail().trim(),req.getOrderId().trim(),req.getProductName().trim(),req.getQuantity(),req.getPrice(),total);
    repository.save(entity);
    String subject="Order Placed Successfully";
    String body=String.format("Hi %s,%n%nYour order has been placed successfully.%nOrder ID: %s%nProduct: %s%nQuantity: %d%nUnit Price: %.2f%nTotal: %.2f%n%nThank you for shopping with us!%nCAMS Team",
      req.getCustomerName(), req.getOrderId(), req.getProductName(), req.getQuantity(), req.getPrice(), total);
    mailService.sendPlainText(req.getEmail(), subject, body);
    return ResponseEntity.ok(new ApiResponse(true, "Saved & email sent to " + req.getEmail()));
  }
  @GetMapping("/health") public ResponseEntity<ApiResponse> health(){ return ResponseEntity.ok(new ApiResponse(true,"OK")); }
}
