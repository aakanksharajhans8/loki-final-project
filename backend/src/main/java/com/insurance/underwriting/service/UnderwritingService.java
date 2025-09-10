package com.insurance.underwriting.service;

import com.insurance.underwriting.dto.*;
import com.insurance.underwriting.model.*;
import com.insurance.underwriting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UnderwritingService {

    private final UnderwritingRuleRepository ruleRepo;
    private final UnderwritingDecisionRepository decisionRepo;

    public ScoreResultDto evaluate(EvaluateRequestDto req) {
        List<UnderwritingRule> rules = ruleRepo.findByActiveTrue();
        ExpressionParser parser = new SpelExpressionParser();
        int score = 0;

        // Setup evaluation context
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("customer", req.getRiskFactors());
        ctx.setVariable("policy", req.getRiskFactors()); // reuse risk map

        StringBuilder details = new StringBuilder();

        for (UnderwritingRule rule : rules) {
            try {
                Boolean matched = parser.parseExpression(rule.getCondition()).getValue(ctx, Boolean.class);
                if (Boolean.TRUE.equals(matched)) {
                    score += rule.getWeight();
                    details.append("Rule matched: ").append(rule.getName()).append("; ");
                }
            } catch (Exception e) {
                details.append("Rule error: ").append(rule.getName()).append("; ");
            }
        }

        String decision = "DECLINED";
        if (score >= 80)
            decision = "APPROVED";
        else if (score >= 40)
            decision = "REFERRED";

        UnderwritingDecision decisionEntity = UnderwritingDecision.builder()
                .policyId(req.getPolicyId())
                .decision(decision)
                .score(score)
                .details(details.toString())
                .build();
        decisionRepo.save(decisionEntity);

        return new ScoreResultDto(decision, score);
    }

    public List<RuleDto> listRules() {
        return ruleRepo.findAll().stream().map(r -> {
            RuleDto dto = new RuleDto();
            dto.setId(r.getId());
            dto.setName(r.getName());
            dto.setCondition(r.getCondition());
            dto.setWeight(r.getWeight());
            dto.setActive(r.getActive());
            return dto;
        }).toList();
    }

    public RuleDto createRule(RuleDto dto) {
        UnderwritingRule rule = UnderwritingRule.builder()
                .name(dto.getName())
                .condition(dto.getCondition())
                .weight(dto.getWeight())
                .active(dto.getActive())
                .build();
        ruleRepo.save(rule);
        dto.setId(rule.getId());
        return dto;
    }
}
