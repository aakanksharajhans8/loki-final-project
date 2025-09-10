package com.insurance.underwriting.service;

import com.insurance.underwriting.dto.EvaluateRequestDto;
import com.insurance.underwriting.dto.ScoreResultDto;
import com.insurance.underwriting.model.UnderwritingRule;
import com.insurance.underwriting.repository.UnderwritingDecisionRepository;
import com.insurance.underwriting.repository.UnderwritingRuleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UnderwritingServiceTest {

    @Test
    void evaluatesScoreFromRules() {
        UnderwritingRuleRepository ruleRepo = Mockito.mock(UnderwritingRuleRepository.class);
        UnderwritingDecisionRepository decRepo = Mockito.mock(UnderwritingDecisionRepository.class);

        when(ruleRepo.findByActiveTrue()).thenReturn(List.of(
                UnderwritingRule.builder()
                        .name("Senior Smoker")
                        .condition("#customer['age']>60 and #customer['smoker']==true")
                        .weight(50).active(true).build(),
                UnderwritingRule.builder()
                        .name("High SI")
                        .condition("#policy['sumInsured']>=1000000")
                        .weight(40).active(true).build()));

        UnderwritingService svc = new UnderwritingService(ruleRepo, decRepo);

        EvaluateRequestDto req = new EvaluateRequestDto();
        req.setPolicyId("P-1");
        req.setRiskFactors(Map.of("age", 65, "smoker", true, "sumInsured", 1500000));

        ScoreResultDto result = svc.evaluate(req);

        assertThat(result.getScore()).isEqualTo(90);
        assertThat(result.getDecision()).isEqualTo("APPROVED");
    }
}
