package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "کاربر یافت نشد.")
public class UserNotFoundException extends RuntimeException {
}
