package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.AnswerContent;
import com.khedmatkar.demo.evaluation.entity.QAContentType;
import com.khedmatkar.demo.evaluation.entity.TextAnswer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
public class AnswerContentDTO {

    @NonNull
    public final QAContentType contentType;

    @JsonProperty("textContent")
    public TextAnswerDTO textAnswerDTO;

    public static AnswerContentDTO from(AnswerContent answerContent) {
        AnswerContentDTO answerContentDTO = AnswerContentDTO.builder()
                .contentType(answerContent.getContentType())
                .build();
        switch (answerContent.getContentType()) {
            case SCORED, MULTIPLE_CHOICE, DOUBLE_CHOICE -> {
                //todo
            }
            case TEXT -> answerContentDTO.textAnswerDTO = TextAnswerDTO.from((TextAnswer) answerContent);
        }
        return answerContentDTO;
    }
}
