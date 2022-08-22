package com.khedmatkar.demo.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
public class EmailServiceProxy implements EmailService {
    private final EmailServiceImpl emailService;

    @Value("${khedmatkar.mail.enabled}")
    private boolean emailEnabled;

    public EmailServiceProxy(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public void send(String to, String subject, String text) {
        if (emailEnabled) {
            emailService.send(to, subject, text);
        } else {
            log.info("skipping sending email due to config disabling it.");
        }
    }
}
