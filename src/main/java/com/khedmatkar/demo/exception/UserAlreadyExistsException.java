package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "another user with this email exists")
public class UserAlreadyExistsException extends RuntimeException {
}
