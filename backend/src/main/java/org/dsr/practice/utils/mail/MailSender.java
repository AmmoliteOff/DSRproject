package org.dsr.practice.utils.mail;

import org.dsr.practice.models.EmailAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailSender {

    private EmailAccount emailAccount;
    public MailSender(@Autowired EmailAccount emailAccount){
        this.emailAccount = emailAccount;
}

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailAccount.getHost());
        mailSender.setPort(emailAccount.getPort());

        mailSender.setUsername(emailAccount.getUsername());
        mailSender.setPassword(emailAccount.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");

        return mailSender;
    }
}