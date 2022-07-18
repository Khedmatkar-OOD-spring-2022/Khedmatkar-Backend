package com.khedmatkar.demo.service.notdomain;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequest;

import java.util.List;

public interface ServiceRequestFinder {

    List<ServiceRequest> getServiceRequests(User user);
}
