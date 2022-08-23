package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "نوع خدمت یافت نشد.")
public class SpecialtyNotFoundException extends RuntimeException {
}
