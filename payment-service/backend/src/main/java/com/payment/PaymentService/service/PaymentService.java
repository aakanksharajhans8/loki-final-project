package com.payment.PaymentService.service;

import com.payment.PaymentService.Entity.TransactionRecord;
import com.payment.PaymentService.repo.TransactionRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class PaymentService {

    private final RazorpayClient client;
    private final TransactionRepo repo;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String secret;

    public PaymentService(RazorpayClient client, TransactionRepo repo) {
        this.client = client;
        this.repo = repo;
    }

    public Map<String, Object> createOrder(
            String customerName,
            String phone,
            String email,
            String policyType,
            String policyNumber,
            Long baseAmountPaise,
            Long taxPaise,
            Long totalAmountPaise
    ) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmountPaise); // amount + tax in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "rcpt_" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);

        TransactionRecord tr = new TransactionRecord();
        tr.setCustomerName(customerName);
        tr.setPhone(phone);
        tr.setEmail(email);
        tr.setPolicyType(policyType);
        tr.setPolicyNumber(policyNumber);
        tr.setAmountPaise(baseAmountPaise);
        tr.setTaxPaise(taxPaise);
        tr.setRazorpayOrderId(order.get("id"));
        tr.setStatus("CREATED");
        repo.save(tr);

        Map<String, Object> resp = new HashMap<>();
        resp.put("orderId", order.get("id"));
        resp.put("amount", totalAmountPaise);
        resp.put("currency", "INR");
        resp.put("key", razorpayKey);
        return resp;
    }

    public boolean verifyAndSave(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature) {
        try {
            boolean isValid = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature, secret);

            TransactionRecord tr = repo.findByRazorpayOrderId(razorpayOrderId);
            if (tr == null) return false;

            tr.setRazorpayPaymentId(razorpayPaymentId);
            tr.setRazorpaySignature(razorpaySignature);
            tr.setStatus(isValid ? "PAID" : "FAILED");
            repo.save(tr);

            return isValid;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean verifySignature(String orderId, String paymentId, String signature, String secret) throws Exception {
        String payload = orderId + "|" + paymentId;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(payload.getBytes());

        String generated = bytesToHex(hash).toLowerCase();
        return generated.equals(signature);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
