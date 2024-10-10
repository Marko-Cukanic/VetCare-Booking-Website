package au.edu.rmit.sept.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Sending sign up confirmation email
    public void sendSignupConfirmationEmail(String toEmail, String fullname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thank you for signing up with VetCare");
        message.setText("Dear " + fullname + ",\n\nThank you for signing up with VetCare! We are excited to have you on board.\n\nBest regards,\nVetCare Team");

        mailSender.send(message);
    }

    // Sending email with PDF attachment
    @Async
    public void sendEmailWithAttachment(String email, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Medical Report");
            helper.setText("Please find the attached medical report.");

            // Attach PDF
            InputStreamSource attachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("medical_report.pdf", attachment);

            // Send the email
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); 
        }
    }

    @Async
    public void sendPrescriptionOrderEmail(String toEmail, String medicationName, int quantity, double price) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Your Prescription Order Confirmation");

            // Constructing the email message
            String emailContent = "Dear Customer,\n\n" +
                    "Thank you for ordering your prescription with VetCare. Here are the details of your order:\n\n" +
                    "Medication Name: " + medicationName + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Total Price: $" + price + "\n\n" +
                    "Your prescription will arrive at your address within 3-4 business days.\n\n" +
                    "Best regards,\nVetCare Team";

            helper.setText(emailContent);

            // Send the email
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

