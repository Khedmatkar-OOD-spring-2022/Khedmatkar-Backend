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
@Table(name = "score_answers")
public class ScoreAnswer extends AnswerContent {

    private Integer score;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.SCORE;

}
