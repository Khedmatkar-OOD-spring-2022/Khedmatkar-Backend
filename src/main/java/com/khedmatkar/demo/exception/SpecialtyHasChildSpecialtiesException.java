package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "speciality has other specialties as children")
public class SpecialtyHasChildSpecialtiesException extends RuntimeException {
}
