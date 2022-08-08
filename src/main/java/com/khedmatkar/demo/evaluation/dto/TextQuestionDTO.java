package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.TextQuestion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TextQuestionDTO {

    public Integer answerWordLength;

    public static TextQuestionDTO from(TextQuestion textQuestion) {
        return TextQuestionDTO.builder()
                .answerWordLength(textQuestion.getAnswerWordLength())
                .build();
    }
}
