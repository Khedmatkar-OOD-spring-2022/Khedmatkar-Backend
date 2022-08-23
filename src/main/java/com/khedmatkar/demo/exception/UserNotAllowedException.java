package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "شما اجازه این کار را ندارید.")
public class UserNotAllowedException extends RuntimeException {
}
