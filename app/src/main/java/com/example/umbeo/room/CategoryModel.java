
package com.example.umbeo.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "categorymodel")
public class CategoryModel implements Serializable
{

    @PrimaryKey(autoGenerate =true)
    private int id;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("_id")
    @Expose
    private String categoryId;


    public CategoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
