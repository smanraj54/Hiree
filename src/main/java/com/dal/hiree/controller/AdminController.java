package com.dal.hiree.controller;

import com.dal.hiree.entity.EmailObj;
import com.dal.hiree.entity.SubscribeReq;
import com.dal.hiree.client.AWSSNSClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "Pong";
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendEmailToAll(@RequestBody EmailObj email) {
        AWSSNSClient snsHelper = AWSSNSClient.getInstance();
        try {
            snsHelper.sendEmail(email.getSubject(), email.getEmailBody());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return "Some error while sending email: " + ex.getMessage();
        }
        return "Email sent successfully";

    }

    @RequestMapping(value = "/profile", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deactivateProfile() {
        return false;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String subscribeTo(@RequestBody SubscribeReq request) {
        AWSSNSClient awssnsHelper = AWSSNSClient.getInstance();
        try {
            awssnsHelper.subscribe(request.getEmailId());
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("Error while  subscribing. Error: %s", e.getMessage());
        }
        return "Subscription request raised successfully";
    }
}
