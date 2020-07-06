package com.example.umbeo;

public class ItemModel {

    private String name;
    private String image;
    public int quantity;

    public ItemModel(String name, String image, int quantity) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
