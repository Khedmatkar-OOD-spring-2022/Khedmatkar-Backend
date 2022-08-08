package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceAnswer;
import com.khedmatkar.demo.evaluation.entity.MultipleChoiceAnswer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceAnswerDTO {

    @NonNull
    public List<Integer> answerChoices;

    public static MultipleChoiceAnswerDTO from(MultipleChoiceAnswer multipleChoiceAnswer) {
        return MultipleChoiceAnswerDTO.builder()
                .answerChoices(new ArrayList<>(multipleChoiceAnswer.getAnswerChoices()))
                .build();
    }
}
