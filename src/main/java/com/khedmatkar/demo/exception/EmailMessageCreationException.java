package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "ارسال ایمیل با مشکل مواجه شده است.")
public class EmailMessageCreationException extends RuntimeException {
}
