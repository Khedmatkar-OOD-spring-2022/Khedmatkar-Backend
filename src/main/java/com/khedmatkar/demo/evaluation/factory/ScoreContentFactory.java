package com.khedmatkar.demo.evaluation.factory;

import com.khedmatkar.demo.evaluation.entity.*;
import com.khedmatkar.demo.exception.AnswerCreationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class ScoreContentFactory implements QAContentAbstractFactory {

    private Integer answerScore;
    private String questionText;
    private Integer maxScore;
    private Integer minScore;


    @Override
    public QuestionContent createQuestionContent() {
        return ScoreQuestion.builder()
                .contentType(QAContentType.SCORE)
                .questionText(questionText)
                .maxScore(Objects.requireNonNullElse(maxScore, ScoreQuestion.DEFAULT_MAX_SCORE))
                .minScore(Objects.requireNonNullElse(minScore, ScoreQuestion.DEFAULT_MIN_SCORE))
                .build();
    }

    @Override
    public AnswerContent createAnswerContent() {
        if (answerScore > maxScore || answerScore < minScore) {
            throw new AnswerCreationException();
        }
        return ScoreAnswer.builder()
                .contentType(QAContentType.SCORE)
                .score(answerScore)
                .build();
    }
}
