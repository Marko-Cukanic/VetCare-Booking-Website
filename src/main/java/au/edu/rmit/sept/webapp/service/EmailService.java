package au.edu.rmit.sept.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSignupConfirmationEmail(String toEmail, String fullname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thank you for signing up with VetCare");
        message.setText("Dear " + fullname + ",\n\nThank you for signing up with VetCare! We are excited to have you on board.\n\nBest regards,\nVetCare Team");

        mailSender.send(message);
    }
}
