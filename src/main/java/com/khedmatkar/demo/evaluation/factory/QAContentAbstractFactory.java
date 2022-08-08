package com.khedmatkar.demo.evaluation.factory;

import com.khedmatkar.demo.evaluation.entity.AnswerContent;
import com.khedmatkar.demo.evaluation.entity.QuestionContent;

public interface QAContentAbstractFactory {

    QuestionContent createQuestionContent();

    AnswerContent createAnswerContent();
}
