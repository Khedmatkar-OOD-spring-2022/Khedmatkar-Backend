package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "خطایی در عملیات پاسخ ارزیابی به وجود آمده است.")
public class AnswerCreationException extends RuntimeException {
}
