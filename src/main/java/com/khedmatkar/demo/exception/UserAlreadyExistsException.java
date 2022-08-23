package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "کاربری قبلا با ایمیل مشابه در سامانه ثبت‌نام کرده است.")
public class UserAlreadyExistsException extends RuntimeException {
}
