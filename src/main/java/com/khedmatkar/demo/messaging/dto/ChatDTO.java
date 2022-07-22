package com.khedmatkar.demo.messaging.dto;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
public class ChatDTO {

    public Long chatId;
    
    public String status;

    @Email
    public String userAEmail;

    @Email
    public String userBEmail;

    public List<MessageDTO> messages;
}
