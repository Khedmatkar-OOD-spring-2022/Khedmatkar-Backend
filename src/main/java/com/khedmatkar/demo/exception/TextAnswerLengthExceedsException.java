package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The size of the answer is over the limit.")
public class TextAnswerLengthExceedsException extends RuntimeException {
}
