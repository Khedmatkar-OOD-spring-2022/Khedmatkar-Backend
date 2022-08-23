package com.khedmatkar.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "چت مورد نظر وجود ندارد.")
public class NoChatFoundException extends RuntimeException {
}
