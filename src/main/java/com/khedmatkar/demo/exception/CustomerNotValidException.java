package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "مشتری متعلق به درخواست خدمت نمی‌باشد.")
public class CustomerNotValidException extends RuntimeException {
}
