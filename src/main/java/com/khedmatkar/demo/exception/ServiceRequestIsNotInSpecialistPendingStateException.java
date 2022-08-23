package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "درخواست خدمت در مرحله پذیرش متخصص نمی‌باشد.")
public class ServiceRequestIsNotInSpecialistPendingStateException extends RuntimeException {
}
