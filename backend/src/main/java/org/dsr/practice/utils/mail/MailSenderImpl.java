package org.dsr.practice.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl{

    private JavaMailSender emailSender;
    private String from;

    public MailSenderImpl(@Value("${email.username}") String from, @Autowired JavaMailSender emailSender){
        this.emailSender = emailSender;
        this.from = from;
    }
    public  void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
