package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dynamicRuleId;

    private String query; // тип запроса
    private List<String> arguments; // аргументы запроса
    private boolean negate; // модификатор отрицания

    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonBackReference
    private Recommendation recommendation;

    public DynamicRule(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public DynamicRule() {

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public Long getDynamicRuleId() {
        return dynamicRuleId;
    }

    public void setDynamicRuleId(Long id) {
        this.dynamicRuleId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return negate == that.negate && Objects.equals(dynamicRuleId, that.dynamicRuleId) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicRuleId, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + dynamicRuleId +
                ", query='" + query + '\'' +
                ", arguments='" + arguments + '\'' +
                ", negate=" + negate +
                '}';
    }
}
