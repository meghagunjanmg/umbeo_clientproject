package com.example.umbeo.response_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserGetProfileResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data implements Serializable
    {

        @SerializedName("delivery_addresses")
        @Expose
        private List<String> deliveryAddresses = null;
        @SerializedName("loyalty_points")
        @Expose
        private Integer loyaltyPoints;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("shop")
        @Expose
        private String shop;
        @SerializedName("gps")
        @Expose
        private String gps;
        @SerializedName("latlng")
        @Expose
        private String latlng;
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("profile_pic")
        @Expose
        private String profile_pic;



        public List<String> getDeliveryAddresses() {
            return deliveryAddresses;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public void setDeliveryAddresses(List<String> deliveryAddresses) {
            this.deliveryAddresses = deliveryAddresses;
        }

        public Integer getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(Integer loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShop() {
            return shop;
        }

        public void setShop(String shop) {
            this.shop = shop;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public String getLatlng() {
            return latlng;
        }

        public void setLatlng(String latlng) {
            this.latlng = latlng;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}