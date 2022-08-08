package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.entity.Question;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@SuperBuilder
public class QuestionDTO {

    public final Long id;

    @NonNull
    @JsonProperty("content")
    public final QuestionContentDTO content;

    @NonNull
    public final UserType answererType;

    public static QuestionDTO from(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .answererType(question.getAnswererType())
                .content(QuestionContentDTO.from(question.getContent()))
                .build();
    }

}
