package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "پیکربندی این تنظیم با مشکل مواجه شده است.")
public class ConfigEntryNotModifiable extends RuntimeException {
}
