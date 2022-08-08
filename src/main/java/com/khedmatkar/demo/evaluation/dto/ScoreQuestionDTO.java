package com.khedmatkar.demo.evaluation.dto;

import com.khedmatkar.demo.evaluation.entity.ScoreQuestion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreQuestionDTO {

    public Integer maxScore;
    public Integer minScore;

    public static ScoreQuestionDTO from(ScoreQuestion scoreQuestion) {
        return ScoreQuestionDTO.builder()
                .maxScore(scoreQuestion.getMaxScore())
                .minScore(scoreQuestion.getMinScore())
                .build();
    }
}
