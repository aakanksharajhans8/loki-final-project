package com.insurance.documents.backend.service;

import com.insurance.documents.backend.dto.ClaimReceiptRequestDto;
import com.insurance.documents.backend.dto.PolicyPdfRequestDto;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generatePolicyPdf(PolicyPdfRequestDto dto) {
        // Escape special characters manually to avoid XML parsing errors
        String policyId = escapeXml(dto.getPolicyId());
        String holder = escapeXml(dto.getPolicyHolderName());
        String coverage = escapeXml(dto.getCoverageDetails());
        String premium = escapeXml(dto.getPremiumAmount());
        String start = dto.getStartDate() != null ? escapeXml(dto.getStartDate().toString()) : "";
        String end = dto.getEndDate() != null ? escapeXml(dto.getEndDate().toString()) : "";

        // Use local assets/company-logo.png for the logo
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns='http://www.w3.org/1999/xhtml'>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>" +
                "<title>Insurance Policy Document</title>" +
                "<style type='text/css'>" +
                "body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }" +
                "h1, h2, h3 { color: #2e6c80; text-align: center; margin-bottom: 10px; }" +
                "p { font-size: 12px; text-align: justify; }" +
                ".header { text-align: center; margin-bottom: 30px; }" +
                ".company-logo { width: 80px; height: 80px; border: 1px solid #ddd; display: inline-block; margin-bottom: 10px; }" +
                ".policy-details { width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 13px; }" +
                ".policy-details th, .policy-details td { border: 1px solid #666; padding: 8px; text-align: left; }" +
                ".policy-details th { background-color: #f2f2f2; width: 30%; }" +
                ".terms { margin-top: 30px; font-size: 12px; }" +
                ".signature { margin-top: 60px; display: flex; justify-content: space-between; }" +
                ".signature div { text-align: center; width: 40%; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                // Use the local asset path for the logo
                "<div class='company-logo'><img src=\"/assets/company-logo.png\" width=\"80\" height=\"80\" alt=\"Logo\"/></div>" +
                "<h1>Insurance Policy Document</h1>" +
                "<p><strong>Issued by: XYZ Insurance Company Pvt. Ltd.</strong></p>" +
                "</div>" +

                "<h2>Policy Summary</h2>" +
                "<table class='policy-details'>" +
                "<tr><th>Policy Number</th><td>" + policyId + "</td></tr>" +
                "<tr><th>Policy Holder Name</th><td>" + holder + "</td></tr>" +
                "<tr><th>Coverage Details</th><td>" + coverage + "</td></tr>" +
                "<tr><th>Premium Amount</th><td>" + premium + "</td></tr>" +
                "<tr><th>Policy Validity</th><td>" + start + " to " + end + "</td></tr>" +
                "</table>" +

                "<div class='terms'>" +
                "<h3>Terms &amp; Conditions</h3>" +
                "<p>1. The insured agrees to abide by all the clauses mentioned in this policy. </p>" +
                "<p>2. The insurance company will indemnify the insured subject to the policy coverage, exclusions, and applicable limits.</p>" +
                "<p>3. Non-disclosure of material facts or misrepresentation shall render this policy void.</p>" +
                "<p>4. Claims must be submitted with proper documentation within 30 days of the incident.</p>" +
                "<p>5. This document is legally binding and governed by the applicable laws of India.</p>" +
                "</div>" +

                "<div class='signature'>" +
                "<div><p>________________________</p><p>Authorized Signatory</p></div>" +
                "<div><p>________________________</p><p>Policy Holder</p></div>" +
                "</div>" +

                "</body>" +
                "</html>";

        return renderHtmlToPdf(html);
    }

    /**
     * Simple XML/HTML escape without extra libraries.
     */
    private String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public byte[] generateClaimReceiptPdf(ClaimReceiptRequestDto dto) {
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns='http://www.w3.org/1999/xhtml'>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>" +
                "<title>Claim Receipt</title>" +
                "<style type='text/css'>" +
                "body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }" +
                "h1, h2 { color: #2e6c80; text-align: center; margin-bottom: 10px; }" +
                "p { font-size: 12px; text-align: justify; }" +
                ".header { text-align: center; margin-bottom: 30px; }" +
                ".receipt-details { width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 13px; }" +
                ".receipt-details th, .receipt-details td { border: 1px solid #666; padding: 8px; text-align: left; }" +
                ".receipt-details th { background-color: #f2f2f2; width: 30%; }" +
                ".footer { margin-top: 40px; font-size: 12px; text-align: center; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<h1>Claim Settlement Receipt</h1>" +
                "<p><strong>Issued by: XYZ Insurance Company Pvt. Ltd.</strong></p>" +
                "</div>" +

                "<h2>Receipt Details</h2>" +
                "<table class='receipt-details'>" +
                "<tr><th>Claim ID</th><td>" + dto.getClaimId() + "</td></tr>" +
                "<tr><th>User ID</th><td>" + dto.getUserId() + "</td></tr>" +
                "<tr><th>Claimant Name</th><td>" + dto.getClaimantName() + "</td></tr>" +
                "<tr><th>Policy ID</th><td>" + dto.getPolicyId() + "</td></tr>" +
                "<tr><th>Amount Paid</th><td>" + dto.getAmountPaid() + "</td></tr>" +
                "<tr><th>Payment Date</th><td>" + dto.getPaymentDate() + "</td></tr>" +
                "</table>" +

                "<div class='footer'>" +
                "<p>This is a system-generated receipt and does not require a physical signature.</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        return renderHtmlToPdf(html);
    }




    private byte[] renderHtmlToPdf(String xhtml) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtml, "file:/"); // Base URI required
            renderer.layout();
            renderer.createPDF(baos);
            renderer.finishPDF();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
