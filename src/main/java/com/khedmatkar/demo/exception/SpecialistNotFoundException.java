package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "specialist not found")
public class SpecialistNotFoundException extends RuntimeException {
}
