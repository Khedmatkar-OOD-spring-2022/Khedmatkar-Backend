package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "مشکل فنی بسته شده است.")
public class TechnicalIssueIsClosedException extends RuntimeException {
}
