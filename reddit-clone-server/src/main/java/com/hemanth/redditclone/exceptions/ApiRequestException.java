package com.hemanth.redditclone.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

@Getter
public class ApiRequestException extends RuntimeException {
    private HttpStatus httpStatus;
    private Map<String, String> errorsMap;
    private List<FieldError> fieldErrors;

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable e) {
        super(message, e);
    }

    public ApiRequestException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public ApiRequestException(String message, Throwable e, Map<String, String> errorsMap, HttpStatus httpStatus) {
        super(message, e);
        this.errorsMap = errorsMap;
        this.httpStatus = httpStatus;
    }
}
