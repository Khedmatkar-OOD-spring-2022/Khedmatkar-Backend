package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "مشکلی در ساخت پرسش ارزیابی به وجود آمده است.")
public class QuestionCreationException extends RuntimeException {
}
