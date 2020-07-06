package com.example.umbeo;

import java.util.List;

public class CategoryModel {

    private String categoryName;
    private List<ItemModel> CategoryItems;

    public CategoryModel(String categoryName, List<ItemModel> categoryItems) {
        this.categoryName = categoryName;
        CategoryItems = categoryItems;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ItemModel> getCategoryItems() {
        return CategoryItems;
    }

    public void setCategoryItems(List<ItemModel> categoryItems) {
        CategoryItems = categoryItems;
    }
}
