package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.QAContentType;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@SuperBuilder
public class QuestionContentDTO {

    @NonNull
    public final QAContentType contentType;

    @NotEmpty
    public final String questionText;

    @JsonProperty("text_content")
    public final TextQuestionDTO textQuestionDTO;
}
