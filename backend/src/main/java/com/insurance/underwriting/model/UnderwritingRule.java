package com.insurance.underwriting.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "underwriting_rules")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UnderwritingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // e.g., "Senior Smoker Rule"
    @Column(columnDefinition = "TEXT")
    private String condition;     // SpEL expression (e.g. "customer.age > 60 && customer.smoker == true")
    private Integer weight;       // score weight
    private Boolean active;       // active/inactive
}
