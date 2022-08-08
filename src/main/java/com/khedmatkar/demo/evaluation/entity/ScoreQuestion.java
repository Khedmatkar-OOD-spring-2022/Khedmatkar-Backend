package com.khedmatkar.demo.evaluation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "score_questions")
public class ScoreQuestion extends QuestionContent {

    public static final Integer DEFAULT_MAX_SCORE = 5;
    public static final Integer DEFAULT_MIN_SCORE = 1;

    private Integer maxScore = DEFAULT_MAX_SCORE;
    private Integer minScore = DEFAULT_MIN_SCORE;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.SCORE;

}
