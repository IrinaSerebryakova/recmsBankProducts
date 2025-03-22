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

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_text")
    private String productText;

    @OneToMany(mappedBy = "recommendation")
    @JsonManagedReference
    private List<DynamicRule> rule;

    @JsonCreator
    public Recommendation(@JsonProperty String productName,
                          @JsonProperty UUID productId,
                          @JsonProperty String productText,
                          @JsonProperty List<DynamicRule> rule) {
        this.productId = productId;
        this.productName = productName;
        this.productText = productText;
        this.rule = rule;
    }

    public Recommendation() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
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
                "\"id\": " + productId + "," +
                "\"name\": " + productName + "," +
                "\"text\": " + productText +
                "}\n" + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId, productText);
    }


    public List<DynamicRule> getRule() {
        return rule;
    }

    public void setRule(List<DynamicRule> dynamicRules) {
        this.rule = dynamicRules;
    }
}
