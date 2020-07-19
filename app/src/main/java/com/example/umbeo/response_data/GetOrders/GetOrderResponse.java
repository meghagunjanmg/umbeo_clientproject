
package com.example.umbeo.response_data.GetOrders;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<OrdersList> data = null;
    private final static long serialVersionUID = 6390176164511745093L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdersList> getData() {
        return data;
    }

    public void setData(List<OrdersList> data) {
        this.data = data;
    }

}
