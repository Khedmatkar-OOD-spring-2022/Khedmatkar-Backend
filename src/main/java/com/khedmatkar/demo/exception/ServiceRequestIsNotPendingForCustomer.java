package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "service is not waiting for customer's acceptance")
public class ServiceRequestIsNotPendingForCustomer extends RuntimeException {
}
