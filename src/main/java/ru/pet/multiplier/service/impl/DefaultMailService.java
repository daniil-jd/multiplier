package ru.pet.multiplier.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.pet.multiplier.exception.mail.SendMimeMailMessageException;

import javax.mail.MessagingException;

@Service
public class DefaultMailService {
    private final JavaMailSender mailSender;
    private final String sender;
    private final String SUBJECT = "Registration in Super-chat";
    private String contentLink = "<a href='http://localhost:8080/api/registration/confirmation?token=%s&username=%s'>Link must be here</a>";

    public DefaultMailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String sender) {
        this.mailSender = mailSender;
        this.sender = sender;
    }

    public void sendRegistrationToken(String username, String token) {
        sendMimeMessage(sender, username, SUBJECT, String.format(contentLink, token, username));
    }

    public void sendMimeMessage(String from, String to, String subject, String content) {
        try {
            var message = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendMimeMailMessageException(e);
        }
    }
}
