package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.evaluation.dto.QuestionContentDTO;
import com.khedmatkar.demo.evaluation.dto.TextQuestionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "text_questions")
public class TextQuestion extends QuestionContent {

    public static final Integer DEFAULT_ANSWER_WORD_LENGTH = 256;

    private Integer answerWordLength = DEFAULT_ANSWER_WORD_LENGTH;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.TEXT;

}
