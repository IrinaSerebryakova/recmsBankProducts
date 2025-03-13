package com.project.command.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID dynamicRuleId;

    private String query; // тип запроса
    private String arguments; // аргументы запроса
    private boolean negate; // модификатор отрицания

    public DynamicRule(){
    }

    public DynamicRule(String query, String arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public UUID getDynamicRuleId() {
        return dynamicRuleId;
    }

    public void setDynamicRuleId(UUID id) {
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
