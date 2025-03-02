package com.project.command.model;

import java.util.Objects;

public class RecmDTO {
    private String productName;
    private String productId;
    private String productText;

    public RecmDTO(String productName, String productId, String productText) {
        this.productName = productName;
        this.productId = productId;
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
                "\"name\": " + productName + "," +
                "\"id\": " + productId + "," +
                "\"text\": " + productText +
                "}\n" + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecmDTO that = (RecmDTO) o;
        return Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId, productText);
    }

}
