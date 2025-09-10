package com.payment.PaymentService.repo;

import com.payment.PaymentService.Entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionRecord, Long> {

    TransactionRecord findByRazorpayOrderId(String orderId);
}
