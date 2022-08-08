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
public class DoubleChoiceContentFactory implements QAContentAbstractFactory {

    private String answerChoice;
    private String questionText;
    private String choice1;
    private String choice2;


    @Override
    public QuestionContent createQuestionContent() {
        return DoubleChoiceQuestion.builder()
                .contentType(QAContentType.DOUBLE_CHOICE)
                .questionText(questionText)
                .choice1(Objects.requireNonNullElse(choice1, DoubleChoiceQuestion.DEFAULT_CHOICE1))
                .choice2(Objects.requireNonNullElse(choice2, DoubleChoiceQuestion.DEFAULT_CHOICE2))
                .build();
    }

    @Override
    public AnswerContent createAnswerContent() {
        if (!(answerChoice.equals(choice1) || answerChoice.equals(choice2))) {
            throw new AnswerCreationException();
        }
        return DoubleChoiceAnswer.builder()
                .contentType(QAContentType.DOUBLE_CHOICE)
                .answerChoice(answerChoice)
                .build();
    }
}
