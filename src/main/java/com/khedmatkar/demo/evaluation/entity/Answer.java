package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
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
@Table(name = "answers")
public class Answer extends AbstractEntity {

    @OneToOne
    private AnswerContent content;

    @OneToOne
    private User user;

}
