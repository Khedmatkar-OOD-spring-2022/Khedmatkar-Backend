package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceAnswer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DoubleChoiceAnswerDTO {

    @NonNull
    public String answerChoice;

    public static DoubleChoiceAnswerDTO from(DoubleChoiceAnswer doubleChoiceAnswer) {
        return DoubleChoiceAnswerDTO.builder()
                .answerChoice(doubleChoiceAnswer.getAnswerChoice())
                .build();
    }
}
