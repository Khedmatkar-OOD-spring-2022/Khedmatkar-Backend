package com.khedmatkar.demo.messaging.dto;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
public class MessageDTO {

    public Long chatId;

    @Email
    public String senderEmail;

    public String text;

    public LocalDateTime timeStamp;

}
