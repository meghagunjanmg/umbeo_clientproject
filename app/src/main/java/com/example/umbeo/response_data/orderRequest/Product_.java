
package com.example.umbeo.response_data.orderRequest;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_ implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;


    @SerializedName("_id")
    @Expose
    private String productId;

    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("description")
    @Expose
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
