package com.kodilla.stockpricemonitorwithalert.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.math.BigDecimal;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${admin.mail}")
    private String adminEmail;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String subject, String text, String toEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(message);

    }

    public void sendPriceAlert(String cryptoName, BigDecimal oldPrice, BigDecimal newPrice) {
        String subject = "Price Alert: " + cryptoName;
        String text = String.format("The price of %s has increased by 10%%. Previous price: %.2f, New price: %.2f",
                cryptoName, oldPrice, newPrice);


        try {
            sendEmail(subject, text, adminEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
