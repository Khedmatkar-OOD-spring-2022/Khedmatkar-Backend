package com.khedmatkar.demo.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khedmatkar.demo.evaluation.entity.*;
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

    @JsonProperty("textContent")
    public TextQuestionDTO textQuestionDTO;

    @JsonProperty("scoreContent")
    public ScoreQuestionDTO scoreQuestionDTO;

    @JsonProperty("doubleChoiceContent")
    public DoubleChoiceQuestionDTO doubleChoiceQuestionDTO;

    @JsonProperty("multipleChoiceContent")
    public MultipleChoiceQuestionDTO multipleChoiceQuestionDTO;

    public static QuestionContentDTO from(QuestionContent questionContent) {
        QuestionContentDTO questionContentDTO = QuestionContentDTO.builder()
                .contentType(questionContent.getContentType())
                .questionText(questionContent.getQuestionText())
                .build();
        switch (questionContent.getContentType()) {
            case TEXT -> questionContentDTO.textQuestionDTO = TextQuestionDTO.from((TextQuestion) questionContent);
            case SCORE -> questionContentDTO.scoreQuestionDTO = ScoreQuestionDTO.from((ScoreQuestion) questionContent);
            case DOUBLE_CHOICE ->
                    questionContentDTO.doubleChoiceQuestionDTO = DoubleChoiceQuestionDTO.from((DoubleChoiceQuestion) questionContent);
            case MULTIPLE_CHOICE ->
                    questionContentDTO.multipleChoiceQuestionDTO = MultipleChoiceQuestionDTO.from((MultipleChoiceQuestion) questionContent);
        }
        return questionContentDTO;
    }
}
