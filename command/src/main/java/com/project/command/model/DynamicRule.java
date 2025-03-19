package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.command.converter.ListToArrayConverter;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query; // тип запроса


    @Convert(converter = ListToArrayConverter.class)
    private List<String> arguments; // аргументы запроса

    private boolean negate; // модификатор отрицания

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    @JsonBackReference
    private Recommendation recommendation;

    @JsonCreator
    public DynamicRule(@JsonProperty String query, @JsonProperty List<String> arguments, @JsonProperty boolean negate) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return negate == that.negate && Objects.equals(id, that.id) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "rule{" +
                "\"query=\"" + query + "," + '\'' +
                "\"arguments='" + arguments + "," + '\'' +
                "\"negate=" + negate +
                '}';
    }
}
