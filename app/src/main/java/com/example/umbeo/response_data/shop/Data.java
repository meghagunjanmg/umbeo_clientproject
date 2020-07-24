
package com.example.umbeo.response_data.shop;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("shop")
    @Expose
    private Shop shop;
    private final static long serialVersionUID = 900951660793691275L;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }




}
