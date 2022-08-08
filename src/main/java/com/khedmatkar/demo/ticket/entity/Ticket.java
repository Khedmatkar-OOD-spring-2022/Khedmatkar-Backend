package com.khedmatkar.demo.ticket.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.ticket.dto.TechnicalIssueDTO;
import com.khedmatkar.demo.ticket.dto.TicketDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tickets")
public class Ticket extends AbstractEntity {

    private String title;

    private String content;

    @ManyToOne
    private User writer;

    public static Ticket from(TicketDTO dto, User writer) {
        return Ticket.builder()
                .writer(writer)
                .title(dto.title)
                .content(dto.content)
                .build();
    }
}
