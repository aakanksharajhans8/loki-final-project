package com.insurance.underwriting.controller;

import com.insurance.underwriting.dto.*;
import com.insurance.underwriting.service.UnderwritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriting")
@RequiredArgsConstructor
public class UnderwritingController {

    private final UnderwritingService service;

    @PostMapping("/evaluate")
    public ResponseEntity<ScoreResultDto> evaluate(@RequestBody EvaluateRequestDto req) {
        return ResponseEntity.ok(service.evaluate(req));
    }

    @GetMapping("/rules")
    public ResponseEntity<List<RuleDto>> listRules() {
        return ResponseEntity.ok(service.listRules());
    }

    @PostMapping("/rules")
    public ResponseEntity<RuleDto> createRule(@RequestBody RuleDto dto) {
        return ResponseEntity.ok(service.createRule(dto));
    }
}
