package com.example.usermanagement.exception;

import com.example.usermanagement.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Global API exception handler.
 *
 * @author liulang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles business exceptions.
     *
     * @param exception business exception
     * @return failed API response
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException exception) {
        log.warn("Business exception: code={}, message={}", exception.getCode(), exception.getMessage());
        return Result.failure(exception.getCode(), exception.getMessage());
    }

    /**
     * Handles invalid request body exceptions.
     *
     * @param exception invalid request body exception
     * @return failed API response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? "request parameter is invalid" : fieldError.getDefaultMessage();
        return Result.failure(400, message);
    }

    /**
     * Handles invalid query parameter binding exceptions.
     *
     * @param exception invalid query parameter binding exception
     * @return failed API response
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? "request parameter is invalid" : fieldError.getDefaultMessage();
        return Result.failure(400, message);
    }

    /**
     * Handles method parameter validation exceptions.
     *
     * @param exception method parameter validation exception
     * @return failed API response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("request parameter is invalid");
        return Result.failure(400, message);
    }

    /**
     * Handles unexpected exceptions.
     *
     * @param exception unexpected exception
     * @return failed API response
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception exception) {
        log.error("Unexpected exception", exception);
        return Result.failure(500, "internal server error");
    }
}
