package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "questionnaires")
public class QuestionnaireItem extends AbstractEntity {

    @OneToOne
    private Question question;

    @OneToOne
    private Answer answer;
}
