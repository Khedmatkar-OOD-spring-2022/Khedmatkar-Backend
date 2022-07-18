package com.khedmatkar.demo.messaging.entity;


import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Message extends AbstractEntity {

    @ManyToOne
    private User sender;

    private String text;
}
