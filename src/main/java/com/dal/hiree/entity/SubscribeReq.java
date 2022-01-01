package com.dal.hiree.entity;

public class SubscribeReq {
    String emailId;

    public SubscribeReq() {

    }
    public SubscribeReq(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmail(String emailId) {
        this.emailId = emailId;
    }
}
