package com.example.umbeo;

import com.example.umbeo.response_data.ProductModel;

import java.util.List;

public class CategoryModel {

    private String categoryName;
    private String categoryId;
    private List<ProductModel> CategoryItems;

    public CategoryModel(String categoryId,String categoryName, List<ProductModel> categoryItems) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        CategoryItems = categoryItems;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductModel> getCategoryItems() {
        return CategoryItems;
    }

    public void setCategoryItems(List<ProductModel> categoryItems) {
        CategoryItems = categoryItems;
    }
}
