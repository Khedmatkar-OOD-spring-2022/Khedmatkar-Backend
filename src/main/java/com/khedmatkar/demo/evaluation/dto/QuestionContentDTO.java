package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.QAContentType;
import com.khedmatkar.demo.evaluation.entity.QuestionContent;
import com.khedmatkar.demo.evaluation.entity.TextQuestion;
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
    public TextQuestionDTO textQuestionDTO;

    public static QuestionContentDTO from(QuestionContent questionContent) {
        QuestionContentDTO questionContentDTO = QuestionContentDTO.builder()
                .contentType(questionContent.getContentType())
                .questionText(questionContent.getQuestionText())
                .build();
        switch (questionContent.getContentType()) {
            case SCORED, MULTIPLE_CHOICE, DOUBLE_CHOICE -> {
            }
            case TEXT -> questionContentDTO.textQuestionDTO = TextQuestionDTO.from((TextQuestion) questionContent);
        }
        return questionContentDTO;
    }
}
