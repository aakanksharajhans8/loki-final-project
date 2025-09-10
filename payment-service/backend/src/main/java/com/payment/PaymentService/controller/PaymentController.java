package com.payment.PaymentService.controller;

import com.payment.PaymentService.Entity.TransactionRecord;
import com.payment.PaymentService.dto.CreateOrderRequest;
import com.payment.PaymentService.repo.TransactionRepo;
import com.payment.PaymentService.service.PaymentService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import java.io.ByteArrayOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired private PaymentService paymentService;
    @Autowired private TransactionRepo repo;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest body) throws Exception {
        long baseAmountPaise = body.getAmount().longValue() * 100;
        long taxPaise = (long) (baseAmountPaise * 0.18); // 18%
        long totalAmountPaise = baseAmountPaise + taxPaise;

        Map<String, Object> resp = paymentService.createOrder(
                body.getName(),
                body.getPhone(),
                body.getEmail(),
                body.getPolicyType(),
                body.getPolicyNumber(),
                baseAmountPaise,
                taxPaise,
                totalAmountPaise);

        return ResponseEntity.ok(resp);
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> payload) {
        String paymentId = payload.get("razorpay_payment_id");
        String orderId = payload.get("razorpay_order_id");
        String signature = payload.get("razorpay_signature");
        boolean ok = paymentService.verifyAndSave(paymentId, orderId, signature);
        return ResponseEntity.ok(Map.of("success", ok));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionRecord>> listTransactions() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(value = "/receipt/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) throws Exception {
        TransactionRecord tr = repo.findById(id).orElseThrow();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Receipt"));
        document.add(new Paragraph("----------------------------"));
        document.add(new Paragraph("Transaction ID: " + tr.getId()));
        document.add(new Paragraph("Name: " + tr.getCustomerName()));
        document.add(new Paragraph("Phone: " + tr.getPhone()));
        document.add(new Paragraph("Email: " + tr.getEmail()));
        document.add(new Paragraph("Policy Number: " + tr.getPolicyNumber()));
        document.add(new Paragraph("Policy Type: " + tr.getPolicyType()));
        document.add(new Paragraph("Amount (Excl. Tax): ₹" + (tr.getAmountPaise() / 100.0)));
        document.add(new Paragraph("Tax (18%): ₹" + (tr.getTaxPaise() / 100.0)));
        document.add(new Paragraph("Total Amount: ₹" + ((tr.getAmountPaise() + tr.getTaxPaise()) / 100.0)));
        document.add(new Paragraph("Razorpay Payment ID: " + tr.getRazorpayPaymentId()));
        document.add(new Paragraph("Status: " + tr.getStatus()));
        document.add(new Paragraph("Date: " + tr.getCreatedAt()));

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=receipt_" + tr.getId() + ".pdf")
                .body(pdfBytes);
    }
}
