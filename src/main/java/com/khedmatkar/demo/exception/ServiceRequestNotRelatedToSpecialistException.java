package com.khedmatkar.demo.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "service request is not related to client")
public class ServiceRequestNotRelatedToSpecialistException extends RuntimeException {
}
