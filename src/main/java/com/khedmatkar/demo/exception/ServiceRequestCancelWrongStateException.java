package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "service request cannot be canceled in this state")
public class ServiceRequestCancelWrongStateException extends RuntimeException {
}
