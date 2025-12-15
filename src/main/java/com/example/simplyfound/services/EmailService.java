package com.example.simplyfound.services;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Async("emailExecutor")
    public CompletableFuture<Boolean> sendConfirmationEmail(String email, String clientName) {
        String subject = "Website Request Received - SimplyFound";
        
        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #f5f5f5;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #808080;'>" +
                        "<h1 style='color: #808080; margin: 0;'>SimplyFound</h1>" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #808080; margin-top: 0;'>Dear " + clientName + ",</h2>" +
                        "<p style='font-size: 16px; color: #333;'>We have received your request. We are processing your request now.</p>" +
                        "<p style='font-size: 16px;'>Our team will review your submission and get back to you shortly. You will receive updates via email.</p>" +
                        "<p style='font-size: 16px;'>Thank you for choosing SimplyFound!</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 SimplyFound. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@simplyfound.com' style='color: #808080; text-decoration: none;'>support@simplyfound.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            logger.info("Confirmation email sent successfully to: {}", email);
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            logger.error("Failed to send confirmation email to: {}. Error: {}", email, e.getMessage(), e);
            // Don't throw exception - just log the error so form submission can continue
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async("emailExecutor")
    public CompletableFuture<Boolean> sendAdminNotificationEmail(Long clientJourneyId, String clientName, String companyName, String clientEmail) {
        String subject = "New Website Request Submitted - SimplyFound";
        String adminPanelLink = frontendUrl + "/admin/dashboard";
        
        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #f5f5f5;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #808080;'>" +
                        "<h1 style='color: #808080; margin: 0;'>SimplyFound</h1>" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #808080; margin-top: 0;'>New Website Request Received</h2>" +
                        "<p style='font-size: 16px; color: #333;'>A new client has submitted a form for a website request.</p>" +
                        "<div style='background-color: #ffffff; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #808080;'>" +
                        "<p style='margin: 5px 0;'><strong>Client Name:</strong> " + clientName + "</p>" +
                        "<p style='margin: 5px 0;'><strong>Company Name:</strong> " + companyName + "</p>" +
                        "<p style='margin: 5px 0;'><strong>Email:</strong> " + clientEmail + "</p>" +
                        "<p style='margin: 5px 0;'><strong>Submission ID:</strong> " + clientJourneyId + "</p>" +
                        "</div>" +
                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + adminPanelLink + "' style='background-color: #808080; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 16px; font-weight: bold;'>View in Admin Panel</a>" +
                        "</div>" +
                        "<p style='font-size: 14px; color: #666;'>Click the button above to access the admin panel and review the full submission details.</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 SimplyFound. All rights reserved.</p>" +
                        "</div>" +
                        "</div>";

        String[] adminEmails = {"yoelowelly@gmail.com", "owellgraphics23@gmail.com"};
        boolean allSent = true;
        for (String adminEmail : adminEmails) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(adminEmail);
                helper.setSubject(subject);
                helper.setText(htmlBody, true);
                mailSender.send(message);
                logger.info("Admin notification email sent successfully to: {}", adminEmail);
            } catch (Exception e) {
                logger.error("Failed to send admin notification email to: {}. Error: {}", adminEmail, e.getMessage(), e);
                allSent = false;
            }
        }

        return CompletableFuture.completedFuture(allSent);
    }
}

