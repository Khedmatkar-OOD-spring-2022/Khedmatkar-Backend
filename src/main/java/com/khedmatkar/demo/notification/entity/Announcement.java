package com.khedmatkar.demo.notification.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="announcements")
@SuperBuilder
@NoArgsConstructor
@Getter
public class Announcement extends AbstractEntity {

    @ManyToOne
    private User user;
    private String message;
}
