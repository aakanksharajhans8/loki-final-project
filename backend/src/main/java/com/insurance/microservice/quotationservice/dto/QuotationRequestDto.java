package com.insurance.microservice.quotationservice.dto;

import lombok.Data;

// This class is a Data Transfer Object used for the request body
// when creating a new quotation.
@Data
public class QuotationRequestDto {
    private Long customerId;
    private Long productId;
}
