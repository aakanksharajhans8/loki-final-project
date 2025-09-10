package com.insurance.underwriting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreResultDto {
    private String decision; // APPROVED / REFERRED / DECLINED
    private int score;
}
