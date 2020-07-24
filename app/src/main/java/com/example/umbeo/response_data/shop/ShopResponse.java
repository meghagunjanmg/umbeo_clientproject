
package com.example.umbeo.response_data.shop;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Shop data;
    private final static long serialVersionUID = -7943000149836544404L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Shop getData() {
        return data;
    }

    public void setData(Shop data) {
        this.data = data;
    }

}
