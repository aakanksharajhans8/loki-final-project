package com.insurance.documents.backend.controller;

import com.insurance.documents.backend.constants.ApiConstants;
import com.insurance.documents.backend.dto.PolicyPdfRequestDto;
import com.insurance.documents.backend.dto.ClaimReceiptRequestDto;
import com.insurance.documents.backend.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH)
public class DocumentController {

    private final PdfGeneratorService pdfGeneratorService;

    public DocumentController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    // ---------------- POLICY PDF ----------------
    @Operation(summary = "Generate a policy PDF dynamically for download",
            description = "Returns a downloadable PDF file for the given policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Policy PDF generated successfully",
                    content = @Content(mediaType = "application/pdf",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/policies/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePolicyPdf(
            @PathVariable String id,
            @RequestBody PolicyPdfRequestDto dto) {
        dto.setPolicyId(id);
        byte[] pdfContent = pdfGeneratorService.generatePolicyPdf(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=policy-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    // ---------------- CLAIM RECEIPT PDF ----------------
    @Operation(summary = "Generate a claim receipt PDF dynamically for download",
            description = "Returns a downloadable PDF file for the given claim")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Claim receipt PDF generated successfully",
                    content = @Content(mediaType = "application/pdf",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/claims/{id}/receipt", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateClaimReceiptPdf(
            @PathVariable String id,
            @RequestBody ClaimReceiptRequestDto dto) {
        dto.setClaimId(id);
        byte[] pdfContent = pdfGeneratorService.generateClaimReceiptPdf(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=claim-receipt-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
