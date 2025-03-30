package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.command.converter.ListToArrayConverter;
import com.project.command.dynamic.constants.QueryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "query")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QueryType query;

    @Convert(converter = ListToArrayConverter.class)
    private List<String> arguments; // аргументы запроса

    private boolean negate; // модификатор отрицания

    @ManyToOne
    @JoinColumn(name = "rule_id")
    @JsonBackReference
    private Rule rule;     //InvestRuleSet напр

    @OneToOne(mappedBy = "query")
    @JsonManagedReference
    private Statistics stats;

    @JsonCreator
    public Query(Long id, @JsonProperty QueryType query, @JsonProperty List<String> arguments, @JsonProperty boolean negate, Rule rule) {
        this.id = id;
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.rule = rule;
    }

    public Query() {

    }

    public QueryType getQuery() {
        return query;
    }

    public void setQuery(QueryType query) {
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
        Query that = (Query) o;
        return negate == that.negate && Objects.equals(id, that.id) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }


    @Override
    public String toString() {
        return "Query{" +
                "id=" + id +
                ", query=" + query +
                ", arguments=" + arguments +
                ", negate=" + negate +
                ", rule=" + rule +
                ", stats=" + stats +
                '}';
    }
}
