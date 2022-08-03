package com.khedmatkar.demo.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.ticket.entity.TechnicalIssue;
import com.khedmatkar.demo.ticket.entity.TechnicalIssueStatus;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuperBuilder
@AllArgsConstructor
public class TechnicalIssueDTO {

    public final Long id;

    public final TechnicalIssueStatus status;

    public final List<TicketDTO> answers;

    @NonNull
    @JsonProperty("ticket")
    public final TicketDTO ticket;

    public static TechnicalIssueDTO from(TechnicalIssue technicalIssue) {
        return TechnicalIssueDTO.builder()
                .id(technicalIssue.getId())
                .ticket(TicketDTO.builder()
                        .id(technicalIssue.getId())
                        .writerEmail(technicalIssue.getWriter().getEmail())
                        .title(technicalIssue.getTitle())
                        .content(technicalIssue.getContent())
                        .timeStamp(technicalIssue.getCreation())
                        .build())
                .status(technicalIssue.getStatus())
                .answers(Optional.of(technicalIssue.getAnswers().stream()
                                .map(TicketDTO::from)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .build();
    }

}
