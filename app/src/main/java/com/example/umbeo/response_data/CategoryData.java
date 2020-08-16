
package com.example.umbeo.response_data;

import java.io.Serializable;
import java.util.List;

import com.example.umbeo.room.CategoryModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryData implements Serializable
{

    @SerializedName("categories")
    @Expose
    private List<CategoryModel> categories = null;
    private final static long serialVersionUID = 4070459790377737009L;

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

}
