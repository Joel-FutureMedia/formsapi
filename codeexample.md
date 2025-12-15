# this code example will be used to send emails to clients to confimr that they have submited thier website requeste look into the code below and use the examples,also the design it well please
package com.example.musicroyalties.services;

import com.example.musicroyalties.models.invoiceAndPayments.ArtistInvoiceReports;
import com.example.musicroyalties.models.invoiceAndPayments.Invoice;
import com.example.musicroyalties.services.invoicesServices.PdfGenForArtistService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PdfInvoiceGenerator pdfInvoiceGenerator;

    private static final String LOGO_URL = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png";
    
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    
//    public void sendProfileRejectionEmail(String email, String notes) {
//        String subject = "Profile Rejection Notification";
//        String body = "Dear Artist,\n\n" +
//                     "Your profile has been rejected by the administrator.\n\n" +
//                     "Reason: " + notes + "\n\n" +
//                     "Please review and resubmit your profile with the necessary corrections.\n\n" +
//                     "Best regards,\nMusic Royalties System";
//        sendEmail(email, subject, body);
//    }

    //profile Rejection
    public void sendProfileRejectionEmail(String email, String notes) {
        String subject = "Profile Rejection Notification";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png"; // Same logo
        String resubmitLink = "https://api.owellserver.ggff.net/profile/resubmit"; // Update with your actual resubmission link

        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #fff8f8;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #e86d6d;'>" +
                        "<img src='" + logoUrl + "' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #cc0000; margin-top: 0;'>Dear Africtune Member,</h2>" +
                        "<p style='font-size: 16px; color: #333;'>We regret to inform you that your profile has been <strong>rejected</strong> by our administrator.</p>" +

                        "<p style='font-size: 16px; color: #cc0000;'><strong>Reason:</strong> " + notes + "</p>" +

                        "<p style='font-size: 16px;'>Please review the feedback above and resubmit your profile after making the necessary corrections.</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + resubmitLink + "' " +
                        "style='background-color: #cc0000; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>" +
                        "Resubmit Profile" +
                        "</a>" +
                        "</div>" +

                        "<p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the following link into your browser:</p>" +
                        "<p style='font-size: 14px; word-break: break-all; color: #cc0000;'>" + resubmitLink + "</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 africtune. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #cc0000; text-decoration: none;'>support@africtune.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // HTML email
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send profile rejection email", e);
        }
    }

    /**
     * Sends a styled HTML email notification for music file rejection.
     */
    public void sendMusicRejectionEmail(String email, String notes) {
        String subject = "Music File Rejection Notification";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png"; // Ensure no extra spaces
        String resubmitLink = "https://api.owellserver.ggff.net/artist/music/resubmit"; // Update with your actual link

        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #fff8f8;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #e86d6d;'>" +
                        "<img src='" + logoUrl + "' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #cc0000; margin-top: 0;'>Dear Africtune Member,</h2>" +
                        "<p style='font-size: 16px; color: #333;'>We regret to inform you that your music submission has been <strong>rejected</strong> by our administrator.</p>" +

                        "<p style='font-size: 16px; color: #cc0000;'><strong>Reason:</strong> " + notes + "</p>" +

                        "<p style='font-size: 16px;'>Please review the feedback above, make the necessary corrections, and resubmit your track.</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + resubmitLink + "' " +
                        "style='background-color: #cc0000; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>" +
                        "Resubmit Music" +
                        "</a>" +
                        "</div>" +

                        "<p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the following link into your browser:</p>" +
                        "<p style='font-size: 14px; word-break: break-all; color: #cc0000;'>" + resubmitLink + "</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 africtune. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #cc0000; text-decoration: none;'>support@africtune.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // Enable HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send music rejection email", e);
        }
    }
//    public void sendMusicRejectionEmail(String email, String notes) {
//        String subject = "Music File Rejection Notification";
//        String body = "Dear Artist,\n\n" +
//                     "Your music file has been rejected by the administrator.\n\n" +
//                     "Reason: " + notes + "\n\n" +
//                     "Please review and resubmit your music file with the necessary corrections.\n\n" +
//                     "Best regards,\nMusic Royalties System";
//        sendEmail(email, subject, body);
//    }
    
//    public void sendProfileApprovalEmail(String email) {
//        String subject = "Profile Approval Notification";
//        String body = "Dear Artist,\n\n" +
//                     "Congratulations! Your profile has been approved by the administrator.\n\n" +
//                     "You can now upload your music files.\n\n" +
//                     "Best regards,\nMusic Royalties System";
//        sendEmail(email, subject, body);
//    }

    //simple Profile Approval
    public void sendProfileApprovalEmail(String email) {
        String subject = "Profile Approval Notification";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png"; // Reuse your logo
        String dashboardLink = "https://api.owellserver.ggff.net/dashboard"; // Link to your system (update if needed)

        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #f9f7ff;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #ed7c08;'>" +
                        "<img src='" + logoUrl + "' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #ed7c08; margin-top: 0;'>Dear Africtune Member,</h2>" +
                        "<p style='font-size: 16px; color: #333;'>Congratulations! Your profile has been <strong>approved</strong> by the administrator.</p>" +
                        "<p style='font-size: 16px;'>You can now upload and manage your music files in the system.</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + dashboardLink + "' " +
                        "style='background-color: #ed7c08; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>" +
                        "Go to Dashboard" +
                        "</a>" +
                        "</div>" +

                        "<p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the following link into your browser:</p>" +
                        "<p style='font-size: 14px; word-break: break-all; color: #ed7c08;'>" + dashboardLink + "</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 Africtune. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #ed7c08; text-decoration: none;'>support@africtune.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // HTML email
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send profile approval email", e);
        }
    }
    //music approval
    /**
     * Sends a styled HTML email notification for music file approval.
     */
    public void sendMusicApprovalEmail(String email) {
        String subject = "Music File Approval Notification";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png";
        String dashboardLink = "https://api.owellserver.ggff.net/artist/dashboard"; // Link to artist dashboard

        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #f7fff7;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #ed7c08;'>" +
                        "<img src='" + logoUrl + "' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #ed7c08; margin-top: 0;'>Dear Africtune Member,</h2>" +
                        "<p style='font-size: 16px; color: #333;'>Congratulations! Your music file has been <strong>approved</strong> by the administrator.</p>" +
                        "<p style='font-size: 16px;'>Your track is now live in the system and available for companies to license and use.</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + dashboardLink + "' " +
                        "style='background-color: #ed7c08; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>" +
                        "View Dashboard" +
                        "</a>" +
                        "</div>" +

                        "<p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the following link:</p>" +
                        "<p style='font-size: 14px; word-break: break-all; color: #ed7c08;'>" + dashboardLink + "</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 Africtune. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #2e7d32; text-decoration: none;'>support@africtune.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // Enable HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send music approval email", e);
        }
    }

//    public void sendMusicApprovalEmail(String email) {
//        String subject = "Music File Approval Notification";
//        String body = "Dear Artist,\n\n" +
//                     "Congratulations! Your music file has been approved by the administrator.\n\n" +
//                     "Your music is now available for companies to select.\n\n" +
//                     "Best regards,\nMusic Royalties System";
//        sendEmail(email, subject, body);
//    }

    public void sendVerificationEmail(String email, String token) {
        String subject = "Email Verification";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png"; // Make sure this URL is accessible
        String verificationLink = "https://api.owellserver.ggff.net/api/auth/verify?token=" + token;

        String htmlBody =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #f9f7ff;'>" +
                        "<div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #ed7c08;'>" +
                        "<img src='" + logoUrl + "' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />" +
                        "</div>" +

                        "<div style='padding: 30px; color: #444; line-height: 1.6;'>" +
                        "<h2 style='color: #ed7c08; margin-top: 0;'>Hello, Africtune Member!</h2>" +
                        "<p style='font-size: 16px; color: #333;'>Thank you for registering with <strong>africtune</strong>.</p>" +
                        "<p style='font-size: 16px;'>To complete your registration, please verify your email address by clicking the button below:</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + verificationLink + "' " +
                        "style='background-color: #ed7c08; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>" +
                        "Verify Email Address" +
                        "</a>" +
                        "</div>" +

                        "<p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the following link into your browser:</p>" +
                        "<p style='font-size: 14px; word-break: break-all; color: #ed7c08;'>" + verificationLink + "</p>" +
                        "</div>" +

                        "<div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>" +
                        "<p>&copy; 2025 Africtune. All rights reserved.</p>" +
                        "<p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #ed7c08; text-decoration: none;'>support@africtune.com</a></p>" +
                        "</div>" +
                        "</div>";

        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = is HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    // invoice pdf
    public void sendInvoiceEmail(String clientEmail, Invoice invoice) {
        String subject = "Invoice Number - " + invoice.getInvoiceNumber() + " - AFRICTUNE ";

        String htmlBody = buildInvoiceHtml(invoice);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // multipart

            helper.setTo(clientEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // HTML

            // Attach PDF
            byte[] pdfBytes = pdfInvoiceGenerator.generatePdf(invoice);
            helper.addAttachment("Invoice_" + invoice.getInvoiceNumber() + ".pdf",
                    new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send invoice email", e);
        }
    }

    private String buildInvoiceHtml(Invoice invoice) {
        return """
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <style>
                /* Reset */
                body, table, td, a { -webkit-text-size-adjust: 100%%; -ms-text-size-adjust: 100%%; }
                table { border-collapse: collapse !important; }
                img { border: 0; height: auto; line-height: 100%%; outline: none; text-decoration: none; max-width: 100%%; }
                body { margin: 0 !important; padding: 0 !important; width: 100%% !important; }

                /* Responsive */
                @media screen and (max-width: 600px) {
                    .container {
                        width: 100%% !important;
                        padding: 15px !important;
                    }
                    .stack {
                        display: block !important;
                        width: 100%% !important;
                        text-align: left !important;
                    }
                    .invoice-table th, .invoice-table td {
                        font-size: 13px !important;
                        padding: 6px !important;
                    }
                }
            </style>
        </head>
        <body style='background-color: #f9f9f9; margin: 0; padding: 0;'>
            <div class="container" style='font-family: Arial, sans-serif; max-width: 700px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 12px; background-color: #ffffff; box-shadow: 0 4px 12px rgba(0,0,0,0.1);'>
                <div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #8a2be2;'>
                    <img src='%s' alt='YTN Africa Logo' style='height: 100px; object-fit: contain; max-width: 100%%;' />
                    <h1 style='color: #333; margin: 10px 0; font-size: 24px;'>INVOICE</h1>
                </div>

                <div style='padding: 25px; color: #333; line-height: 1.6; font-size: 15px;'>

                    <!-- Company & Client Info -->
                    <table width='100%%' style='margin-bottom: 25px;'>
                        <tr>
                            <td class="stack" width='50%%' style='vertical-align: top; padding-right: 10px;'>
                                <strong>From:</strong><br/>
                                <strong>AFRICTUNE</strong><br/>
                                %s<br/>
                                Phone: %s<br/>
                                Email: %s<br/>
                                Contact: %s
                            </td>
                            <td class="stack" width='50%%' style='text-align: right; vertical-align: top;'>
                                <strong>Bill To:</strong><br/>
                                <strong>%s</strong><br/>
                                %s<br/>
                                Phone: %s<br/>
                                Email: %s
                            </td>
                        </tr>
                    </table>

                    <!-- Invoice Details -->
                    <table class="invoice-table" width='100%%' style='margin-bottom: 25px; border-collapse: collapse;'>
                        <tr>
                            <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Invoice #</th>
                            <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Date</th>
                            <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Service</th>
                        </tr>
                        <tr>
                            <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                            <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                            <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                        </tr>
                    </table>

                    <!-- Amounts -->
                    <table width='100%%' style='margin-bottom: 25px; border-collapse: collapse;'>
                        <tr style='background-color: #f9f9f9;'>
                            <td style='padding: 10px;'><strong>Units Used:</strong> %d</td>
                            <td style='padding: 10px;'><strong>Unit Price:</strong> N$%s</td>
                        </tr>
                        <tr>
                            <td style='padding: 10px;'><strong>Total Amount:</strong> N$%s</td>
                            <td style='padding: 10px;'><strong>Net Amount:</strong> N$%s</td>
                        </tr>
                    </table>

                    <!-- Bank Info -->
                    <div style='background-color: #f0f8ff; padding: 15px; border-radius: 8px; margin-bottom: 20px;'>
                        <strong>Payment Instructions:</strong><br/>
                        Bank: %s<br/>
                        Branch: %s<br/>
                        Account Number: %d
                    </div>

                    <!-- Footer -->
                    <div style='text-align: center; padding: 15px; color: #777; font-size: 13px; border-top: 1px solid #eee; margin-top: 20px;'>
                        <p>&copy; 2025 africtune. All rights reserved.</p>
                        <p>If you have questions, contact <a href='mailto:support@africtune.com' style='color: #8a2be2;'>support@africtune.com</a></p>
                    </div>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
                LOGO_URL,
                invoice.getCompanyAddress(),
                invoice.getCompanyPhone(),
                invoice.getCompanyEmail(),
                invoice.getContactPerson(),
                invoice.getBillingToCompanyName(),
                invoice.getBillingToCompanyAddress(),
                invoice.getBillingToCompanyPhone(),
                invoice.getBillingToCompanyEmail(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getInvoiceServiceType(),
                invoice.getTotalUsed(),
                format(invoice.getUnitPrice()),
                format(invoice.getTotalAmount()),
                format(invoice.getTotalNetAmount()),
                invoice.getBankName(),
                invoice.getBranchName(),
                invoice.getAccountNumber()
        );
    }


    private String format(Double value) {
        if (value == null) return "0.00";
        return String.format("%.2f", value);
    }

    //

    //send email for payments
    // artist payment pdf
    public void sendPayment(String artistEmail, ArtistInvoiceReports artistInvoice) {
        String subject = "Payment Report - " + artistInvoice.getPaymentId() + " - AFRICTUNE ";

        String htmlBody = buildArtistPaymentHtml(artistInvoice);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // multipart

            helper.setTo(artistEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // HTML

            // Attach PDF
            byte[] pdfBytes = new PdfGenForArtistService().generatePdf(artistInvoice);
            helper.addAttachment("PaymentReport_" + artistInvoice.getPaymentId() + ".pdf",
                    new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send artist payment email", e);
        }
    }

    private String buildArtistPaymentHtml(ArtistInvoiceReports artistInvoice) {
        return """
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <style>
            body, table, td, a { -webkit-text-size-adjust: 100%%; -ms-text-size-adjust: 100%%; }
            table { border-collapse: collapse !important; }
            img { border: 0; height: auto; line-height: 100%%; outline: none; text-decoration: none; max-width: 100%%; }
            body { margin: 0 !important; padding: 0 !important; width: 100%% !important; }
            @media screen and (max-width: 600px) {
                .container { width: 100%% !important; padding: 15px !important; }
                .stack { display: block !important; width: 100%% !important; text-align: left !important; }
            }
        </style>
    </head>
    <body style='background-color: #f9f9f9; margin: 0; padding: 0;'>
        <div class="container" style='font-family: Arial, sans-serif; max-width: 700px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 12px; background-color: #ffffff; box-shadow: 0 4px 12px rgba(0,0,0,0.1);'>
            <div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #8a2be2;'>
                <img src='%s' alt='YTN Africa Logo' style='height: 100px; object-fit: contain; max-width: 100%%;' />
                <h1 style='color: #333; margin: 10px 0; font-size: 24px;'>Proof of Payment</h1>
            </div>

            <div style='padding: 25px; color: #333; line-height: 1.6; font-size: 15px;'>
                <!-- Company & Artist Info -->
                <table width='100%%' style='margin-bottom: 25px;'>
                    <tr>
                        <td class="stack" width='50%%' style='vertical-align: top; padding-right: 10px;'>
                            <strong>From:</strong><br/>
                            <strong>AFRICTUNE</strong><br/>
                            %s<br/>
                            Phone: %s<br/>
                            Email: %s<br/>
                            Contact: %s
                        </td>
                        <td class="stack" width='50%%' style='text-align: right; vertical-align: top;'>
                            <strong>Payments To:</strong><br/>
                            <strong>%s</strong><br/>
                            Phone: %s<br/>
                            Email: %s<br/>
                            Artist ID: %s
                        </td>
                    </tr>
                </table>

                <!-- Payment Details -->
                <table width='100%%' style='margin-bottom: 25px; border-collapse: collapse;'>
                    <tr>
                        <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Payment #</th>
                        <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Date</th>
                        <th style='text-align: left; padding: 8px; background-color: #f5f5f5;'>Description</th>
                    </tr>
                    <tr>
                        <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                        <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                        <td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>
                    </tr>
                </table>

                <!-- Amounts -->
                <table width='100%%' style='margin-bottom: 25px; border-collapse: collapse;'>
                    <tr style='background-color: #f9f9f9;'>
                        <td style='padding: 10px;'><strong>Quantity:</strong> %s</td>
                        <td style='padding: 10px;'><strong>Unit Price:</strong> N$%s</td>
                    </tr>
                    <tr>
                        <td style='padding: 10px;'><strong>Total Earned:</strong> N$%s</td>
                        <td style='padding: 10px;'><strong>Net Paid:</strong> N$%s</td>
                    </tr>
                </table>

                <!-- Bank Info -->
                <div style='background-color: #f0f8ff; padding: 15px; border-radius: 8px; margin-bottom: 20px;'>
                    <strong>Payment Instructions:</strong><br/>
                    Bank: %s<br/>
                    Branch: %s<br/>
                    Account Number: %d
                </div>

                <!-- Footer -->
                <div style='text-align: center; padding: 15px; color: #777; font-size: 13px; border-top: 1px solid #eee; margin-top: 20px;'>
                    <p>&copy; 2025 africtune. All rights reserved.</p>
                    <p>If you have questions, contact <a href='mailto:support@africtune.com' style='color: #8a2be2;'>support@africtune.com</a></p>
                </div>
            </div>
        </div>
    </body>
    </html>
    """.formatted(
                LOGO_URL,
                artistInvoice.getCompanyAddress(),
                artistInvoice.getCompanyPhone(),
                artistInvoice.getCompanyEmail(),
                artistInvoice.getContactPerson(),
                artistInvoice.getArtistName(),
                artistInvoice.getArtistPhoneNumber(),
                artistInvoice.getArtistEmail(),
                artistInvoice.getArtistId(),
                artistInvoice.getPaymentId(),
                artistInvoice.getPaymentDate(),
                artistInvoice.getDesciption(),
                artistInvoice.getTotalplayed(),
                format(artistInvoice.getUnitPrice()),
                format(artistInvoice.getTotalEarned()),
                format(artistInvoice.getTotalNetpaid()),
                artistInvoice.getBankName(),
                artistInvoice.getBranchName(),
                artistInvoice.getAccountNumber()
        );
    }


    //Section to send and Implement forgot Password
    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset Request";
        String logoUrl = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png";
        String resetLink = "https://portal.africtune.ggff.net/auth/reset-password?token=" + token; // Frontend URL
        // If you don't have a frontend yet, you can point to a temporary endpoint later

        String htmlBody = """
        <div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px; background-color: #f9f7ff;'>
            <div style='text-align: center; padding-bottom: 20px; border-bottom: 2px solid #ed7c08;'>
                <img src='%s' alt='YTN Africa Logo' style='height: 100px; object-fit: contain;' />
            </div>
            <div style='padding: 30px; color: #444; line-height: 1.6;'>
                <h2 style='color: #ed7c08; margin-top: 0;'>Password Reset</h2>
                <p style='font-size: 16px; color: #333;'>You requested a password reset. Click the button below to set a new password.</p>
                <div style='text-align: center; margin: 30px 0;'>
                    <a href='%s' 
                       style='background-color: #ed7c08; color: white; padding: 14px 28px; text-decoration: none; font-size: 16px; border-radius: 6px; display: inline-block; font-weight: bold;'>
                       Reset Password
                    </a>
                </div>
                <p style='font-size: 14px; color: #666;'>If the button doesn't work, copy and paste the link below:</p>
                <p style='font-size: 14px; word-break: break-all; color: #ed7c08;'>%s</p>
                <p style='font-size: 14px; color: #999; margin-top: 20px;'>This link expires in 1 hour.</p>
            </div>
            <div style='text-align: center; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 13px;'>
                <p>&copy; 2025 africtune. All rights reserved.</p>
                <p>For support, contact us at <a href='mailto:support@africtune.com' style='color: #ed7c08; text-decoration: none;'>support@africtune.com</a></p>
            </div>
        </div>
        """.formatted(logoUrl, resetLink, resetLink);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}

# these code below is example of how you will be generating prompt files into pdf please. use the same dependecnies please 
package com.example.musicroyalties.services.invoicesServices;// src/main/java/com/example/musicroyalties/services/PdfInvoiceGenerator.java



import com.example.musicroyalties.models.invoiceAndPayments.ArtistInvoiceReports;
import com.example.musicroyalties.models.invoiceAndPayments.Invoice;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PdfGenForArtistService {

    private static final String LOGO_URL = "https://africtune.ggff.net/assets/logo-DOUqIMrq.png";

    public byte[] generatePdf(ArtistInvoiceReports artistinvoice) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Set up PDF writer and document
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add Logo and Title
            ImageData imageData = ImageDataFactory.create(LOGO_URL);
            Image logo = new Image(imageData).setWidth(120); // Adjust size as needed
            logo.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.add(logo);

            // Invoice Title
            Paragraph title = new Paragraph("Proof of Payments")
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setMarginTop(10f);
            document.add(title);

            // Spacer
            document.add(new Paragraph("\n"));

            // Invoice Info Table (2 columns: From & Bill To)
            Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            headerTable.setMarginTop(10);

            // FROM (Sender)
            Cell fromCell = new Cell().setBorder(null);
            fromCell.add(new Paragraph("From:").setBold());
            fromCell.add(new Paragraph("YTNAfrica / NASCAM"));
            fromCell.add(new Paragraph(artistinvoice.getCompanyAddress()));
            fromCell.add(new Paragraph("Phone: " + artistinvoice.getCompanyPhone()));
            fromCell.add(new Paragraph("Email: " + artistinvoice.getCompanyEmail()));
            fromCell.add(new Paragraph("Contact: " + artistinvoice.getContactPerson()));
            headerTable.addCell(fromCell);

            // payments TO (Client)
            Cell billToCell = new Cell().setBorder(null);
            billToCell.add(new Paragraph("Payments To:").setBold());
            billToCell.add(new Paragraph(artistinvoice.getArtistName()));
            billToCell.add(new Paragraph(artistinvoice.getArtistPhoneNumber()));
            billToCell.add(new Paragraph("Email: " + artistinvoice.getCompanyEmail()));
            billToCell.add(new Paragraph("ArtistID: " + artistinvoice.getArtistId()));
            headerTable.addCell(billToCell);

            document.add(headerTable);

            // Invoice Details Table (Single row: Number, Date, Service)
            Table infoTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
            infoTable.setMarginTop(20);
            infoTable.addHeaderCell(createHeaderCell("Payment Number"));
            infoTable.addHeaderCell(createHeaderCell("Date"));
            infoTable.addHeaderCell(createHeaderCell("Service"));

            infoTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(artistinvoice.getPaymentId())));
            infoTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(artistinvoice.getPaymentDate())));
            infoTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(artistinvoice.getDesciption())));

            document.add(infoTable);

            // Itemized Table (Units, Unit Price, Total, Net)
            Table itemTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
            itemTable.setMarginTop(20);

            itemTable.addHeaderCell(createHeaderCell("Description"));
            itemTable.addHeaderCell(createHeaderCell("Quantity"));
            itemTable.addHeaderCell(createHeaderCell("Unit Price (NAD)"));
            itemTable.addHeaderCell(createHeaderCell("Total (NAD)"));

            // Add service row
            itemTable.addCell(new Cell().setTextAlignment(TextAlignment.LEFT).add(new Paragraph(artistinvoice.getDesciption())));
            itemTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(artistinvoice.getTotalplayed()))));
            itemTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).add(new Paragraph("N$" + format(artistinvoice.getUnitPrice()))));
            itemTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).add(new Paragraph("N$" + format(artistinvoice.getTotalEarned()))));

            // Net Amount Row
            itemTable.addCell(new Cell(1, 3) // colspan 3
                    .setBorderTop(null)
                    .setBorderLeft(null)
                    .setBorderRight(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph("Net Amount:").setBold()));
            itemTable.addCell(new Cell()
                    .setBorderTop(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph("N$" + format(artistinvoice.getTotalNetpaid())).setBold()));

            document.add(itemTable);

            // Payment Instructions
            document.add(new Paragraph("\nPayment Instructions").setBold().setMarginTop(20));
            document.add(new Paragraph("Bank: " + artistinvoice.getBankName()));
            document.add(new Paragraph("Branch: " + artistinvoice.getAccountNumber()));
            document.add(new Paragraph("Account Number: " + artistinvoice.getBranchName()));

            // Terms & Conditions
            document.add(new Paragraph("\nTerms & Conditions").setBold().setMarginTop(20));
            document.add(new Paragraph(
                    "1. This report is provided for informational purposes and reflects transactions recorded up to the report date.\n" +
                            "2. Final payment amounts are subject to contract terms, adjustments, and applicable taxes.\n" +
                            "3. The report is confidential and intended solely for the artist; unauthorized sharing is prohibited.\n" +
                            "4. Any discrepancies must be reported within 14 days of receipt"
            ).setFontSize(10).setFontColor(ColorConstants.GRAY));




            // Close document
            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF invoice", e);
        }
    }

    private Cell createHeaderCell(String text) {
        return new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .add(new Paragraph(text));
    }

    private String format(Double value) {
        if (value == null) return "0.00";
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
    }

    //invoice number


}

#to upload a logo use this example  below
package com.example.musicroyalties.services;

import com.example.musicroyalties.models.PassportPhoto;
import com.example.musicroyalties.models.User;
import com.example.musicroyalties.repositories.PassportPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PassportPhotoService {
    
    @Autowired
    private PassportPhotoRepository passportPhotoRepository;
    
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";
    
    public PassportPhoto postPhoto(MultipartFile file, String imageTitle, User user) throws IOException {
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
        
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                + "_" + System.currentTimeMillis()
                + originalFileName.substring(originalFileName.lastIndexOf('.'));
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        String fileUrl = "https://api.owellserver.ggff.net/api/passportphoto/view/" + fileName;
        PassportPhoto photo = new PassportPhoto();
        photo.setFileType(file.getContentType());
        photo.setImageUrl(fileUrl);
        photo.setImageTitle(imageTitle);
        photo.setDatePosted(LocalDate.now());
        photo.setUser(user);
        return passportPhotoRepository.save(photo);
    }
    
    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            if (!filePath.startsWith(uploadPath)) {
                throw new RuntimeException("Invalid file path - attempted directory traversal");
            }
            
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + fileName);
            }
            
            if (!Files.isReadable(filePath)) {
                throw new RuntimeException("File is not readable: " + fileName);
            }
            
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
            
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed file URL for: " + fileName + ". Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file: " + fileName + ". Error: " + e.getMessage());
        }
    }
    
    public List<PassportPhoto> getAll() {
        return passportPhotoRepository.findAll();
    }
    
    public Optional<PassportPhoto> getById(Long id) {
        return passportPhotoRepository.findById(id);
    }
    
    public Optional<PassportPhoto> getByUserId(Long userId) {
        return passportPhotoRepository.findByUserId(userId);
    }
    
    public void delete(Long id) {
        passportPhotoRepository.deleteById(id);
    }
    
    public PassportPhoto updatePhoto(Long id, MultipartFile file, String imageTitle) throws IOException {
        PassportPhoto photo = passportPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        
        if (file != null && !file.isEmpty()) {
            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }
            
            String originalFileName = file.getOriginalFilename();
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                    + "_" + System.currentTimeMillis()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            String fileUrl = "https://api.owellserver.ggff.net/api/passportphoto/view/" + fileName;
            photo.setFileType(file.getContentType());
            photo.setImageUrl(fileUrl);
        }
        photo.setImageTitle(imageTitle);
        photo.setDatePosted(LocalDate.now());
        return passportPhotoRepository.save(photo);
    }
}

# code for login and user
package com.example.simplyfoundapis.controllers;

import com.example.simplyfoundapis.models.User;
import com.example.simplyfoundapis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"https://simplyfound.vercel.app", "https://newgate.simplyfound.com.na","https://newgate.simplyfound.com.na/admin","https://newgateinvestments.simplyfound.com.na"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        exposedHeaders = {"Content-Disposition", "Content-Type"},
        allowCredentials = "true")

public class UserController {
    @Autowired
    private UserService userService;

    // POST - Create new user
    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return userService.saveNewUser(user);
    }

    // GET - Retrieve all users
    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    // GET - Retrieve user by ID
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable int id) {
        return userService.findById(id);
    }

    // PUT - Update user by ID
    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    // DELETE - Delete user by ID
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        userService.deleteById(id);
    }
    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String response = userService.login(email, password);
        return ResponseEntity.ok(response);
    }
}

package com.example.simplyfoundapis.services;

import com.example.simplyfoundapis.models.User;
import com.example.simplyfoundapis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // POST - Create new user
    public User saveNewUser(User newUser) {
        return userRepository.save(newUser);
    }

    // GET - Retrieve all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // GET - Retrieve user by ID
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    // DELETE - Delete user by ID
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    // PUT - Update user info
    public User updateUser(User newUser) {
        User oldUser = userRepository.findById(newUser.getId()).get();
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setName(newUser.getName());
        return userRepository.save(oldUser);
    }

    // Login functionality
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful!";
        } else {
            return "Invalid email or password!";
        }
    }
}