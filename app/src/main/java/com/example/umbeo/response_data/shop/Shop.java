
package com.example.umbeo.response_data.shop;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop implements Serializable
{

    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("subCategories")
    @Expose
    private List<Object> subCategories = null;
    @SerializedName("deliverySlots")
    @Expose
    private List<String> deliverySlots = null;
    @SerializedName("deliveryCharges")
    @Expose
    private List<String> deliveryCharges = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("id")
    @Expose
    private String id;
    private final static long serialVersionUID = -3666617508907742631L;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Object> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Object> subCategories) {
        this.subCategories = subCategories;
    }

    public List<String> getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(List<String> deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    public List<String> getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(List<String> deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
