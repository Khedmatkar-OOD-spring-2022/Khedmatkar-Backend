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
@Table(name = "double_choice_questions")
public class DoubleChoiceQuestion extends QuestionContent {

    public static final String DEFAULT_CHOICE1 = "YES";
    public static final String DEFAULT_CHOICE2 = "NO";

    private String choice1 = DEFAULT_CHOICE1;
    private String choice2 = DEFAULT_CHOICE2;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.DOUBLE_CHOICE;

}
