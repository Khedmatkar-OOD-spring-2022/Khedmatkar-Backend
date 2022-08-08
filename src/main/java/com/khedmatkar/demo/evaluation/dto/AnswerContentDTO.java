package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.*;
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

    @JsonProperty("scoreContent")
    public ScoreAnswerDTO scoreAnswerDTO;

    @JsonProperty("doubleChoiceContent")
    public DoubleChoiceAnswerDTO doubleChoiceAnswerDTO;

    @JsonProperty("multipleChoiceContent")
    public MultipleChoiceAnswerDTO multipleChoiceAnswerDTO;

    public static AnswerContentDTO from(AnswerContent answerContent) {
        AnswerContentDTO answerContentDTO = AnswerContentDTO.builder()
                .contentType(answerContent.getContentType())
                .build();
        switch (answerContent.getContentType()) {
            case TEXT -> answerContentDTO.textAnswerDTO = TextAnswerDTO.from((TextAnswer) answerContent);
            case SCORE -> answerContentDTO.scoreAnswerDTO = ScoreAnswerDTO.from((ScoreAnswer) answerContent);
            case DOUBLE_CHOICE ->
                    answerContentDTO.doubleChoiceAnswerDTO = DoubleChoiceAnswerDTO.from((DoubleChoiceAnswer) answerContent);
            case MULTIPLE_CHOICE ->
                    answerContentDTO.multipleChoiceAnswerDTO = MultipleChoiceAnswerDTO.from((MultipleChoiceAnswer) answerContent);
        }
        return answerContentDTO;
    }
}
