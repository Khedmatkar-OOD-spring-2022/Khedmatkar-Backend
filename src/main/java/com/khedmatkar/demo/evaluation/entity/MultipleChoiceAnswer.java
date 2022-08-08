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
@Table(name = "multiple_choice_answers")
public class MultipleChoiceAnswer extends AnswerContent {

    @ElementCollection
    private List<Integer> answerChoices;

    @Enumerated(EnumType.STRING)
    private QAContentType contentType = QAContentType.MULTIPLE_CHOICE;

}
