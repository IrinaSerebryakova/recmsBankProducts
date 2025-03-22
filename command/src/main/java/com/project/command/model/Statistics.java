package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "stats")
public class Statistics {

    @Id
    @Column(name = "rule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    private int count;

    @OneToOne
    @JoinColumn(name = "rule_id")
    @JsonBackReference
    private DynamicRule dynamicRule;


    @JsonCreator
    public Statistics(@JsonProperty DynamicRule dynamicRule, @JsonProperty int count) {
        this.dynamicRule = dynamicRule;
        this.count = count;
    }

    public Statistics() {

    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DynamicRule getDynamicRule() {
        return dynamicRule;
    }

    public void setDynamicRule(DynamicRule dynamicRule) {
        this.dynamicRule = dynamicRule;
    }
}
