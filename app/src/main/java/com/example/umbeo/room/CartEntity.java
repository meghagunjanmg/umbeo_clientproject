package com.example.umbeo.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items_table")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String item_name;
    private int quantity;
    private int price;

    public CartEntity(String item_name, int quantity, int price) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
