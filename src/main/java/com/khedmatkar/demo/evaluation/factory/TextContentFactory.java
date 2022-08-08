package com.khedmatkar.demo.evaluation.factory;

import com.khedmatkar.demo.evaluation.entity.*;
import com.khedmatkar.demo.exception.TextAnswerLengthExceedsException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class TextContentFactory implements QAContentAbstractFactory {

    private String answerText;

    private String questionText;

    private Integer answerWordLength;


    @Override
    public QuestionContent createQuestionContent() {
        return TextQuestion.builder()
                .contentType(QAContentType.TEXT)
                .questionText(questionText)
                .answerWordLength(Objects.requireNonNullElse(answerWordLength, TextQuestion.DEFAULT_ANSWER_WORD_LENGTH))
                .build();
    }

    @Override
    public AnswerContent createAnswerContent() {
        if (answerText.length() > answerWordLength) {
            throw new TextAnswerLengthExceedsException();
        }
        return TextAnswer.builder()
                .contentType(QAContentType.TEXT)
                .text(answerText)
                .build();
    }
}
