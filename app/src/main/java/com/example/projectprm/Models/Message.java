package com.example.projectprm.Models;


import java.util.Date;

public class Message {
    private String message;
    private String email;

    public Message() {
    }

    public Message(String message,String email) {
        this.message = message;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return email;
    }

    public void setSenderId(String senderId) {
        this.email = senderId;
    }

}
