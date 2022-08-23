package com.khedmatkar.demo.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "متخصص مربوط به درخواست خدمت نمی‌باشد.")
public class ServiceRequestNotRelatedToSpecialistException extends RuntimeException {
}
