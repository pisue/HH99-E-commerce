package com.hh99.ecommerce.common.exception;

import com.hh99.ecommerce.common.response.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GeneralExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleBadRequestBody() {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-001", "wrong request body");
    }

    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleRequestValid(org.springframework.validation.BindException exception) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors())
            builder.append("[field] ").append(fieldError.getField()).append(" [message] ").append(fieldError.getDefaultMessage()).append(" [rejectedValue] ").append(fieldError.getRejectedValue());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-002", builder.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleRequestValid() {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-003", "잘못된 데이터 요청");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMethodNotSupport() {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-004", "잘못된 Mapping 요청");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-005", "잘못된 parameter 입니다. value: " + exception.getValue());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-006", "RequestParameter가 존재하지 않습니다. 필요한 RequestParam: " + exception.getParameterName());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleIllegalArgument(IllegalArgumentException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "System-007", exception.getMessage());
    }
}
