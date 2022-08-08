package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.TextAnswer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TextAnswerDTO {

    @NotEmpty
    public String text;

    public static TextAnswerDTO from(TextAnswer textAnswer) {
        return TextAnswerDTO.builder()
                .text(textAnswer.getText())
                .build();
    }
}
