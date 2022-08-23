package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "نوع خدمت مدنظر دارای نوع خدمت فرزند می‌باشد.")
public class SpecialtyHasChildSpecialtiesException extends RuntimeException {
}
