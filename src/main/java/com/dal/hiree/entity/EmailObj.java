package com.dal.hiree.entity;

import java.util.List;

public class EmailObj {
    String subject;
    String emailBody;
    List<String> to;
    String from;

    public EmailObj(String subject, String emailBody, List<String> to, String from) {
        this.subject = subject;
        this.emailBody = emailBody;
        this.to = to;
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
