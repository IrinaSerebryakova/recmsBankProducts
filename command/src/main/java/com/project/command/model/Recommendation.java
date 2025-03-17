package com.project.command.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private UUID productId;
    private String productName;
    private String productText;

    @OneToMany(mappedBy = "recommendation")
    @JsonManagedReference
    private List<DynamicRule> dynamicRules;

    public Recommendation(String productName, UUID productId, String productText, List<DynamicRule>  dynamicRules) {
        this.productId = productId;
        this.productName = productName;
        this.productText = productText;
        this.dynamicRules = dynamicRules;
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


}
