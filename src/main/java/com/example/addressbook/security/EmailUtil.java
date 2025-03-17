package com.example.addressbook.security;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    public void sendEmail(String to, String subject, String body) {
       //email details
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
