package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private UUID product_id;
    private String product_name;
    private String product_text;

    @OneToMany(mappedBy = "recommendation")
    @JsonManagedReference
    private List<DynamicRule> rule;

    @JsonCreator
    public Recommendation(@JsonProperty String product_name,
                          @JsonProperty UUID product_id,
                          @JsonProperty String product_text,
                          @JsonProperty List<DynamicRule> rule) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_text = product_text;
        this.rule = rule;
    }

    public Recommendation() {

    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String productName) {
        this.product_name = productName;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID productId) {
        this.product_id = productId;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String productText) {
        this.product_text = productText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "recommendations: [\n" + "{\n" +
                "\"id\": " + product_id + "," +
                "\"name\": " + product_name + "," +
                "\"text\": " + product_text +
                "}\n" + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(product_name, that.product_name) && Objects.equals(product_id, that.product_id) && Objects.equals(product_text, that.product_text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_name, product_id, product_text);
    }


    public List<DynamicRule> getRule() {
        return rule;
    }

    public void setRule(List<DynamicRule> dynamicRules) {
        this.rule = dynamicRules;
    }
}
