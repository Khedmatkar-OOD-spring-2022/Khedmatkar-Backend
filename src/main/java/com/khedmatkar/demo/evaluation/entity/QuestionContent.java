package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.evaluation.dto.QuestionContentDTO;
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
@Table(name = "question_contents")
public class QuestionContent extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private QAContentType contentType;

    private String questionText;

}
