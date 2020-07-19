
package com.example.umbeo.response_data.orderRequest;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest implements Serializable
{

    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("shopId")
    @Expose
    private String shopId;
    @SerializedName("totalAmount")
    @Expose
    private Integer totalAmount;

    @SerializedName("orderStatus")
    @Expose
    private Integer status;
    private final static long serialVersionUID = -2557480166792349327L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

}
