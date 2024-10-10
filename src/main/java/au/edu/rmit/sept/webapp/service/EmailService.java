package au.edu.rmit.sept.webapp.service;

import java.time.LocalDate;

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

    // Sending booking confirmation email
    @Async
    public void sendBookingConfirmationEmail(String toEmail, String clinicName, LocalDate bookingDate, String timeSlot) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Booking Confirmation - VetCare");
        message.setText("Dear User,\n\nYour booking has been confirmed at " + clinicName +
                ".\n\nDetails:\nDate: " + bookingDate + "\nTime: " + timeSlot +
                "\n\nThank you for choosing VetCare.\n\nBest regards,\nVetCare Team");

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
}
