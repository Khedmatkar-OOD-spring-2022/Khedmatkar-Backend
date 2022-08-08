package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.ScoreAnswer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreAnswerDTO {

    @NonNull
    public Integer score;

    public static ScoreAnswerDTO from(ScoreAnswer scoreAnswer) {
        return ScoreAnswerDTO.builder()
                .score(scoreAnswer.getScore())
                .build();
    }
}
