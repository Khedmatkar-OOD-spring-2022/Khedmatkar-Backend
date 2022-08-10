package com.khedmatkar.demo.notification.service;

import com.khedmatkar.demo.exception.EmailMessageCreationException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Component
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

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