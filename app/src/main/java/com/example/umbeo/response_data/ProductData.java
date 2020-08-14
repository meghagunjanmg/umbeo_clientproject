
package com.example.umbeo.response_data;

import java.io.Serializable;
import java.util.List;

import com.example.umbeo.room.ProductEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductData implements Serializable
{

    @SerializedName("products")
    @Expose
    private List<ProductEntity> products = null;
    private final static long serialVersionUID = 5365860148571437665L;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

}
