package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.DoubleChoiceQuestion;
import com.khedmatkar.demo.evaluation.entity.MultipleChoiceQuestion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionDTO {

    public Boolean isSingleSelection;

    public List<String> choices;

    public static MultipleChoiceQuestionDTO from(MultipleChoiceQuestion multipleChoiceQuestion) {
        return MultipleChoiceQuestionDTO.builder()
                .choices(new ArrayList<>(multipleChoiceQuestion.getChoices()))
                .isSingleSelection(multipleChoiceQuestion.getIsSingleSelection())
                .build();
    }
}
