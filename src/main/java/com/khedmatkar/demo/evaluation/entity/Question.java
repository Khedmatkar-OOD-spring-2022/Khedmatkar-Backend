package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.evaluation.dto.QuestionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "questions")
public class Question extends AbstractEntity {

    @OneToOne(cascade = CascadeType.REMOVE)
    private QuestionContent content;

    @Enumerated(EnumType.STRING)
    private UserType answererType;

    public static Question from(QuestionDTO dto) {
        return Question.builder()
                .id(dto.id)
                .answererType(dto.answererType)
                .build();
    }
}
