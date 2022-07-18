package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.messaging.entity.Chat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ServiceRequestSpecialist extends AbstractEntity {

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private ServiceRequest serviceRequest;

    @Enumerated(EnumType.STRING)
    private ServiceRequestSpecialistStatus status;

    @OneToOne
    private Chat chat;
}
