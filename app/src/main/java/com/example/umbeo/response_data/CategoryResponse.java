
package com.example.umbeo.response_data;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private CategoryData data;
    private final static long serialVersionUID = -8959237023619789999L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CategoryData getData() {
        return data;
    }

    public void setData(CategoryData data) {
        this.data = data;
    }

}
