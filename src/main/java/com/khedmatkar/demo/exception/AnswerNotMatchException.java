package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "نوع پاسخ ارزیابی مناسب نوع پرسش ارزیابی نمی‌باشد.")
public class AnswerNotMatchException extends RuntimeException {
}
