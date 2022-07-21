package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class ServiceRequestAcceptanceCommand {
    private User user;
    private Long serviceRequestId;
    private AcceptanceAction action;

    public enum AcceptanceAction {
        ACCEPT,
        REJECT
    }
}
