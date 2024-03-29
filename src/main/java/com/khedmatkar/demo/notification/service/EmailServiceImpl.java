package com.khedmatkar.demo.notification.service;

import com.khedmatkar.demo.exception.EmailMessageCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
@PropertySource("classpath:application.properties")
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            throw new EmailMessageCreationException();
        }
        try {
            javaMailSender.send(mimeMessage);
        } catch (MailSendException e) {
            LOGGER.error("failed to send email", e);
        }
    }
}