package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

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
    private Query query;

    @JsonCreator
    public Statistics(@JsonProperty Query query, @JsonProperty int count) {
        this.query = query;
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

    public Query getDynamicRule() {
        return query;
    }

    public void setDynamicRule(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "ruleId=" + ruleId +
                ", query=" + query +
                ", count=" + count +
                '}';
    }
}
