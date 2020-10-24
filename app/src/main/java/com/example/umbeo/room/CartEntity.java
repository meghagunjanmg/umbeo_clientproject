package com.example.umbeo.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items_table")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String productId;
    private String categoryId;
    private String subCategoryId;
    private String description;
    private int quantity;
    private double price;
    private double discount;

    public CartEntity(String name, String productId, String categoryId, String subCategoryId, String description, int quantity, double price, double discount) {
        this.name = name;
        this.productId = productId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
