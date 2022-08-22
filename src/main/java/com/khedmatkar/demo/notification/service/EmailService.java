package com.khedmatkar.demo.notification.service;

public interface EmailService {

    void send(String to, String subject, String text);
}
