package com.insurance.underwriting.dto;

import lombok.Data;

@Data
public class RuleDto {
    private Long id;
    private String name;
    private String condition;
    private Integer weight;
    private Boolean active;
}
