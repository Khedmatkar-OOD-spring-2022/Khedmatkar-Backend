package com.khedmatkar.demo.ticket.dto;

import com.khedmatkar.demo.ticket.entity.Feedback;
import com.khedmatkar.demo.ticket.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@SuperBuilder
public class TicketDTO {

    public final Long id;

    @Email
    public final String writerEmail;

    @NotBlank
    public final String title;

    @NotBlank
    public final String content;

    public final LocalDateTime timeStamp;

    public static TicketDTO from(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .writerEmail(ticket.getWriter().getEmail())
                .title(ticket.getTitle())
                .content(ticket.getContent())
                .timeStamp(ticket.getCreation())
                .build();
    }
}
