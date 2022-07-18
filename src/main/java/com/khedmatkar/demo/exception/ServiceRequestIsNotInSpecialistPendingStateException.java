package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "service request is not in specialist pending state")
public class ServiceRequestIsNotInSpecialistPendingStateException extends RuntimeException {
}
