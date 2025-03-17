package com.example.addressbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendResetToken(String toEmail, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset Request");
            message.setText("Here is your password reset token: " + resetToken + "\n\n"
                    + "It will expire in 15 minutes. Use this token in the /reset-password API.");

            mailSender.send(message);
            log.info("Reset token email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Error sending reset token email: {}", e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }
}
