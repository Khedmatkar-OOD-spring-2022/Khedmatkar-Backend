package com.khedmatkar.demo.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class EmailService {
//    private final JavaMailSender javaMailSender;


//    @Value("${spring.mail.username}")
//    private String sender;

    @Async
    public void send(String to, String subject, String text) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//        try {
//            helper.setFrom(sender);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//        } catch (MessagingException e) {
//            throw new EmailMessageCreationException();
//        }
//        try {
//            javaMailSender.send(mimeMessage);
//        } catch (MailSendException e) {
//            LOGGER.error("failed to send email", e);
//        }
    }
}