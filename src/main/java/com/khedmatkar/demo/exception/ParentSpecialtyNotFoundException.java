package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "نوع خدمت پدر یافت نشد.")
public class ParentSpecialtyNotFoundException extends RuntimeException {
}
