
package com.example.umbeo.response_data;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel implements Serializable
{

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    private final static long serialVersionUID = 2260069181476409375L;

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

}
