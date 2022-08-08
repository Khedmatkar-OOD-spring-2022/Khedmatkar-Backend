package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "answer_content")
public class AnswerContent extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private QAContentType contentType;
}
