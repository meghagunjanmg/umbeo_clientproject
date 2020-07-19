
package com.example.umbeo.response_data.GetOrders;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersList implements Serializable
{

    @SerializedName("confirmedByUser")
    @Expose
    private Boolean confirmedByUser;
    @SerializedName("confirmedByShop")
    @Expose
    private Boolean confirmedByShop;
    @SerializedName("cancelledByUser")
    @Expose
    private Boolean cancelledByUser;
    @SerializedName("cancelledByShop")
    @Expose
    private Boolean cancelledByShop;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("shopId")
    @Expose
    private String shopId;
    @SerializedName("orderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("totalAmount")
    @Expose
    private Integer totalAmount;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    private final static long serialVersionUID = 5554102151593117099L;

    public Boolean getConfirmedByUser() {
        return confirmedByUser;
    }

    public void setConfirmedByUser(Boolean confirmedByUser) {
        this.confirmedByUser = confirmedByUser;
    }

    public Boolean getConfirmedByShop() {
        return confirmedByShop;
    }

    public void setConfirmedByShop(Boolean confirmedByShop) {
        this.confirmedByShop = confirmedByShop;
    }

    public Boolean getCancelledByUser() {
        return cancelledByUser;
    }

    public void setCancelledByUser(Boolean cancelledByUser) {
        this.cancelledByUser = cancelledByUser;
    }

    public Boolean getCancelledByShop() {
        return cancelledByShop;
    }

    public void setCancelledByShop(Boolean cancelledByShop) {
        this.cancelledByShop = cancelledByShop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
