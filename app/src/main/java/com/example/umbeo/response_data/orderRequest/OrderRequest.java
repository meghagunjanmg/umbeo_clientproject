
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
    private double totalAmount;

    @SerializedName("orderStatus")
    @Expose
    private Integer status;

    @SerializedName("deliveryAdress")
    @Expose
    private String deliveryAdress;

    @SerializedName("deliverySlot")
    @Expose
    private String deliverySlot;

    @SerializedName("deliveryInstructions")
    @Expose
    private String deliveryInstructions;
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

    public String getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAdress = deliveryAdress;
    }

    public String getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(String deliverySlot) {
        this.deliverySlot = deliverySlot;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
