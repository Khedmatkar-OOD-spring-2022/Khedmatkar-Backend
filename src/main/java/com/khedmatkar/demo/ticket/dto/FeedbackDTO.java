package com.khedmatkar.demo.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@SuperBuilder
public class FeedbackDTO {

    @Email
    public final String writerEmail;

    @NotBlank
    public final String title;

    @NotBlank
    public final String content;

    public final LocalDateTime timeStamp;

}
