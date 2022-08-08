package com.khedmatkar.demo.evaluation.factory;

import com.khedmatkar.demo.evaluation.entity.*;
import com.khedmatkar.demo.exception.AnswerCreationException;
import com.khedmatkar.demo.exception.EntityNotFoundException;
import com.khedmatkar.demo.exception.QuestionCreationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class MultipleChoiceContentFactory implements QAContentAbstractFactory {

    private List<Integer> answerChoices;
    private String questionText;
    private List<String> choices;
    private Boolean isSingleSelection;


    @Override
    public QuestionContent createQuestionContent() {
        return MultipleChoiceQuestion.builder()
                .contentType(QAContentType.MULTIPLE_CHOICE)
                .questionText(questionText)
                .choices(Optional.ofNullable(choices).orElseThrow(QuestionCreationException::new))
                .isSingleSelection(Objects.requireNonNullElse(isSingleSelection, MultipleChoiceQuestion.DEFAULT_IS_SINGLE_SELECTION))
                .build();
    }

    @Override
    public AnswerContent createAnswerContent() {
        if (isSingleSelection && answerChoices.size() > 1) {
            throw new AnswerCreationException();
        }
        return MultipleChoiceAnswer.builder()
                .contentType(QAContentType.MULTIPLE_CHOICE)
                .answerChoices(answerChoices)
                .build();
    }
}
