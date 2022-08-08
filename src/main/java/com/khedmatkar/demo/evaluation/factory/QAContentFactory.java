package com.khedmatkar.demo.evaluation.factory;

import com.khedmatkar.demo.evaluation.entity.AnswerContent;
import com.khedmatkar.demo.evaluation.entity.QuestionContent;

public class QAContentFactory {

    public static QuestionContent getQuestionContent(QAContentAbstractFactory factory) {
        return factory.createQuestionContent();
    }

    public static AnswerContent getAnswerContent(QAContentAbstractFactory factory) {
        return factory.createAnswerContent();
    }
}
