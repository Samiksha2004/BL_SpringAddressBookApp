package com.example.addressbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailSenderServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendResetToken_Success() {
        // Arrange
        String toEmail = "test@example.com";
        String resetToken = "123456";

        // Act
        emailSenderService.sendResetToken(toEmail, resetToken);

        // Assert (verify mailSender.send() was called with expected message)
        verify(mailSender, times(1)).send(argThat((SimpleMailMessage message) ->
                message.getTo()[0].equals(toEmail) &&
                        message.getSubject().equals("Password Reset Request") &&
                        message.getText().contains(resetToken)
        ));
    }

    @Test
    void testSendResetToken_Failure() {
        // Arrange
        String toEmail = "test@example.com";
        String resetToken = "123456";

        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert (expect RuntimeException due to failure)
        try {
            emailSenderService.sendResetToken(toEmail, resetToken);
        } catch (RuntimeException e) {
            assert e.getMessage().equals("Failed to send email");
        }

        // Verify that mailSender.send() was still attempted
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}