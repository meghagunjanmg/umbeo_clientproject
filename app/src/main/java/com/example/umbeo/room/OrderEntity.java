package com.example.umbeo.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "orders")
public class OrderEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Boolean confirmedByUser;

    private Boolean confirmedByShop;

    private Boolean cancelledByUser;

    private Boolean cancelledByShop;

    private String orderId;
    private int status;
    private double amount;
    private String date;
    private String instruction;

    private String product_quantity;

    public OrderEntity(Boolean confirmedByUser, Boolean confirmedByShop, Boolean cancelledByUser, Boolean cancelledByShop, String orderId, int status, double amount, String date, String instruction, String product_quantity) {
        this.confirmedByUser = confirmedByUser;
        this.confirmedByShop = confirmedByShop;
        this.cancelledByUser = cancelledByUser;
        this.cancelledByShop = cancelledByShop;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.date = date;
        this.instruction = instruction;
        this.product_quantity = product_quantity;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
