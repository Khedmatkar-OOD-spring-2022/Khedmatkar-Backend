package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "درخواست خدمت در این مرحله نمی‌تواند لغو شود.")
public class ServiceRequestCancelWrongStateException extends RuntimeException {
}
