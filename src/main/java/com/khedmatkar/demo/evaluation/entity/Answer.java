package com.khedmatkar.demo.evaluation.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequest;
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
@Table(name = "answers")
public class Answer extends AbstractEntity {

    @OneToOne(cascade = CascadeType.REMOVE)
    private AnswerContent content;

    @OneToOne
    private User answerer;

    @OneToOne
    private Question question;

    @ManyToOne
    private ServiceRequest serviceRequest;
}
