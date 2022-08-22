package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.evaluation.entity.Answer;
import com.khedmatkar.demo.exception.NoChatFoundException;
import com.khedmatkar.demo.messaging.entity.Chat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "service_requests")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ServiceRequest extends AbstractEntity {

    // todo: add ServiceDetails Object (See class diagram)

    @ManyToOne
    private Specialty specialty;
    private String description;
    private String address;

    private Date receptionDate;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Specialist acceptedSpecialist;

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.REMOVE)
    private List<ServiceRequestSpecialist> specialistHistory;

    @Enumerated(EnumType.STRING)
    private ServiceRequestStatus status;

    @OneToMany(mappedBy = "serviceRequest")
    private List<Answer> customerQuestionnaire;
    @OneToMany(mappedBy = "serviceRequest")
    private List<Answer> specialistQuestionnaire;

    public List<Chat> getChats() {
        return this.getSpecialistHistory().stream()
                .map(ServiceRequestSpecialist::getChat)
                .collect(Collectors.toList());
    }

    public Chat getChat() {
        return Optional.ofNullable(this.getChats())
                .orElseThrow(NoChatFoundException::new)
                .get(this.getChats().size() - 1);
    }
}
