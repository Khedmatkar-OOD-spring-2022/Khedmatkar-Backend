package com.khedmatkar.demo.messaging.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
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
@Table(name = "chats")
public class Chat extends AbstractEntity {

    @OneToOne
    private ServiceRequestSpecialist serviceRequestSpecialist;

    @ManyToOne
    private User userA;

    @ManyToOne
    private User userB;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
