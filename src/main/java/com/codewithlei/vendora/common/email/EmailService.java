package com.codewithlei.vendora.common.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendSellerApplicationConfirmation(String firstname , String lastname , String email){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Your Seller Application Has Been Received");
        mail.setText("""
            Hi %s %s,

            Thank you for applying to become a seller on our vendora marketplace.

            We have received your application and our team is now reviewing it. \
            This usually takes 1 to 3 business days. You will get another email \
            once a decision has been made.

            In the meantime, there is nothing else you need to do.

            If you did not submit this application, please ignore this email \
            or contact our support team.

            Best regards,
            The Vendora Team
            """.formatted(firstname , lastname));
        mailSender.send(mail);
    }
}
