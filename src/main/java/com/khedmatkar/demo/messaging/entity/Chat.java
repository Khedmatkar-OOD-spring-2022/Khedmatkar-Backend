package com.khedmatkar.demo.messaging.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Chat extends AbstractEntity {

    @ManyToOne
    private User userA;

    @ManyToOne
    private User userB;

    @OneToMany
    private List<Message> messages;
}
