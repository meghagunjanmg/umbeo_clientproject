package com.example.umbeo.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items_table")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String categoryId;
    private String subCategoryId;
    private String description;
    private int quantity;
    private int price;
    private int discount;

    public CartEntity(String name, String categoryId, String subCategoryId, String description, int quantity, int price) {
        this.name = name;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
