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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    public DynamicRule getDynamicRule() {
        return dynamicRule;
    }

    public void setDynamicRule(DynamicRule dynamicRule) {
        this.dynamicRule = dynamicRule;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return count == that.count && Objects.equals(dynamicRule, that.dynamicRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicRule, count);
    }


    @Override
    public String toString() {
        return "stats{ \n" +
                "\"count\": " + count + '\n' +
                "\"rule_id\": " + dynamicRule.getId() + '\n' +
                '}';
    }
}
