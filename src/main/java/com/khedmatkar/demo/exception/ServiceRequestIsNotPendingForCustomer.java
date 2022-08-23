package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "درخواست خدمت در مرحله پذیرش مشتری نمی‌باشد.")
public class ServiceRequestIsNotPendingForCustomer extends RuntimeException {
}
