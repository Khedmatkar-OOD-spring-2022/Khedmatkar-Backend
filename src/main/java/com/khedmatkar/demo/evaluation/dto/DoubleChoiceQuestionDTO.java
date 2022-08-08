package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceQuestion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DoubleChoiceQuestionDTO {

    public String choice1;
    public String choice2;

    public static DoubleChoiceQuestionDTO from(DoubleChoiceQuestion doubleChoiceQuestion) {
        return DoubleChoiceQuestionDTO.builder()
                .choice1(doubleChoiceQuestion.getChoice1())
                .choice2(doubleChoiceQuestion.getChoice2())
                .build();
    }
}
