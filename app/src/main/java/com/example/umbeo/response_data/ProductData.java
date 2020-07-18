
package com.example.umbeo.response_data;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductData implements Serializable
{

    @SerializedName("products")
    @Expose
    private List<ProductModel> products = null;
    private final static long serialVersionUID = 5365860148571437665L;

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

}
