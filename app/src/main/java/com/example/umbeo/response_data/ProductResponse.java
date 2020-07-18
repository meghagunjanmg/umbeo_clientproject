
package com.example.umbeo.response_data;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ProductData data;
    private final static long serialVersionUID = -132420150801085105L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductData getData() {
        return data;
    }

    public void setData(ProductData data) {
        this.data = data;
    }

}
