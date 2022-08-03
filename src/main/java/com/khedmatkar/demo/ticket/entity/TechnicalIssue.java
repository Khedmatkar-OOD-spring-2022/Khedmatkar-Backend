package com.khedmatkar.demo.ticket.entity;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.ticket.dto.TechnicalIssueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TechnicalIssue extends Ticket {

    @Enumerated(EnumType.STRING)
    private TechnicalIssueStatus status = TechnicalIssueStatus.OPENED;

    @OneToMany(cascade = CascadeType.REMOVE, targetEntity = Ticket.class)
    private List<Ticket> answers;

    public static TechnicalIssue from(TechnicalIssueDTO dto, User writer) {
        return TechnicalIssue.builder()
                .writer(writer)
                .title(dto.ticket.title)
                .content(dto.ticket.content)
                .status(dto.status)
                .build();
    }
}
