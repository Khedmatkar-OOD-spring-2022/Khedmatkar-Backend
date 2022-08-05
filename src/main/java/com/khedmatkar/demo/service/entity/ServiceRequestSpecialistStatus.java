package com.khedmatkar.demo.service.entity;

import java.util.List;

public enum ServiceRequestSpecialistStatus {
    WAITING_FOR_SPECIALIST_ACCEPTANCE,
    REJECTED_BY_SPECIALIST,
    WAITING_FOR_CUSTOMER_ACCEPTANCE,
    REJECTED_BY_CUSTOMER,
    ACCEPTED;


    public static List<ServiceRequestSpecialistStatus> ONGOING_STATUSES =
            List.of(ServiceRequestSpecialistStatus.ACCEPTED,
                    ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST_ACCEPTANCE,
                    ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE);
}
