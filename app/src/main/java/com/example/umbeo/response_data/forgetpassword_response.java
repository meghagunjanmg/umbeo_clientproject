package com.example.umbeo.response_data;

public class forgetpassword_response {
    private String status,message;

    public forgetpassword_response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
