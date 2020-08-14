package com.example.umbeo;

import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.room.ProductEntity;

import java.util.List;

public class CategoryModel {

    private String categoryName;
    private String categoryId;
    private List<ProductEntity> CategoryItems;

    public CategoryModel(String categoryId,String categoryName, List<ProductEntity> categoryItems) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        CategoryItems = categoryItems;
    }

    public CategoryModel(String categoryName, List<ProductEntity> productModels) {
        this.categoryName = categoryName;
        CategoryItems = productModels;
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

    public List<ProductEntity> getCategoryItems() {
        return CategoryItems;
    }

    public void setCategoryItems(List<ProductEntity> categoryItems) {
        CategoryItems = categoryItems;
    }
}
