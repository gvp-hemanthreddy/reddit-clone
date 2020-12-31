package com.hemanth.redditclone.exceptions;

import com.hemanth.redditclone.exceptions.apierror.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity handleDefaultException(Exception ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex) {
        HttpStatus httpStatus = ex.getHttpStatus() != null ? ex.getHttpStatus() : BAD_REQUEST;
        ApiError apiError = buildApiError(httpStatus, ex.getMessage());
        List<FieldError> fieldErrors = ex.getFieldErrors();
        if (fieldErrors != null) {
            apiError.addValidationErrors(fieldErrors);
        }
        Map<String, String> errorsMap = ex.getErrorsMap();
        if (errorsMap != null) {
            apiError.addErrors(errorsMap);
        }
        return buildResponseEntity(apiError);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = buildApiError(BAD_REQUEST, "Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

        return buildResponseEntity(apiError);
    }

    private ApiError buildApiError(HttpStatus httpStatus, String message) {
        return ApiError.builder()
                .status(httpStatus)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return ResponseEntity
                .status(apiError.getStatus())
                .body(apiError);
    }
}
