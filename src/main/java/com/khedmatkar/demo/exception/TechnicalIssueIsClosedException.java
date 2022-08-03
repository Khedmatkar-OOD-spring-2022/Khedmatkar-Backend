package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "the technical issue is closed.")
public class TechnicalIssueIsClosedException extends RuntimeException {
}
