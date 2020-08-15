package com.example.umbeo.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop")
public class ShopEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String deliverySlots;
    private double deliveryCharge;

    public ShopEntity(String deliverySlots, double deliveryCharge) {
        this.deliverySlots = deliverySlots;
        this.deliveryCharge = deliveryCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(String deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
}
