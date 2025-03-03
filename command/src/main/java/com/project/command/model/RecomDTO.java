package com.project.command.model;

import java.util.Objects;

public class RecomDTO {
    private String productId;
    private String productName;
    private String productText;

    public RecomDTO(String productName, String productId, String productText) {
        this.productId = productId;
        this.productName = productName;
        this.productText = productText;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
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
        RecomDTO that = (RecomDTO) o;
        return Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId, productText);
    }

}
