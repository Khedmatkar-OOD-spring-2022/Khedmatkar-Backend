package com.khedmatkar.demo.evaluation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "multiple_choice_questions")
public class MultipleChoiceQuestion extends QuestionContent {

    public static final Boolean DEFAULT_IS_SINGLE_SELECTION = true;

    @ElementCollection
    private List<String> choices;

    private Boolean isSingleSelection = DEFAULT_IS_SINGLE_SELECTION;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.MULTIPLE_CHOICE;

}
