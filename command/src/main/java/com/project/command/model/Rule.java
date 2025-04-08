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
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_text")
    private String productText;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Query> queries;

    @JsonCreator
    public Rule(@JsonProperty String productName,
                @JsonProperty UUID productId,
                @JsonProperty String productText) {
        this.productId = productId;
        this.productName = productName;
        this.productText = productText;
    }

    public Rule() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule1 = (Rule) o;
        return Objects.equals(id, rule1.id) && Objects.equals(productId, rule1.productId) && Objects.equals(productName, rule1.productName) && Objects.equals(productText, rule1.productText) && Objects.equals(queries, rule1.queries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, productName, productText, queries);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productText='" + productText + '\'' +
                '}';
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

}
