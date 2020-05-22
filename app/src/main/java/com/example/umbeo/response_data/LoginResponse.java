package com.example.umbeo.response_data;

public class LoginResponse {
    private String status,token;
    private com.example.umbeo.response_data.user user;

    public LoginResponse(String status, String token, com.example.umbeo.response_data.user user) {
        this.status = status;
        this.token = token;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public com.example.umbeo.response_data.user getUser() {
        return user;
    }
}
