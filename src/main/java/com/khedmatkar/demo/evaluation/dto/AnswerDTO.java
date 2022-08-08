package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;

@AllArgsConstructor
@SuperBuilder
public class AnswerDTO {

    public final Long id;

    @NonNull
    public final Long questionId;

    @NonNull
    @JsonProperty("content")
    public final AnswerContentDTO content;

    @Email
    public final String answererEmail;

    public final Long serviceRequestId;

    public static AnswerDTO from(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .serviceRequestId(answer.getServiceRequest().getId())
                .answererEmail(answer.getUser().getEmail())
                .content(AnswerContentDTO.from(answer.getContent()))
                .build();
    }

}
