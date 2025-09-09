package com.insurance.microservice.quotationservice.dto;

import lombok.Data;

// DTO for the user to accept or reject the quote.
@Data
public class AcceptanceRequestDto {
    private boolean accepted;
}
