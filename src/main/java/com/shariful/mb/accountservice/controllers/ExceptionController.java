package com.shariful.mb.accountservice.controllers;

import com.shariful.mb.accountservice.entities.dtos.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalTransactionStateException.class)
    public ResponseEntity<Object> handleIllegalState(IllegalTransactionStateException illegalTransactionStateException) {
        ApiErrorDto apiErrorDto = new ApiErrorDto( HttpStatus.NOT_ACCEPTABLE,illegalTransactionStateException);
        return buildResponseEntity(apiErrorDto);
    }

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<Object> handleIllegalRequest(InvalidParameterException invalidParameterException) {
        ApiErrorDto apiErrorDto = new ApiErrorDto( HttpStatus.BAD_REQUEST,invalidParameterException);
        return buildResponseEntity(apiErrorDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> serverError(RuntimeException runtimeException) {
        ApiErrorDto apiErrorDto = new ApiErrorDto( HttpStatus.INTERNAL_SERVER_ERROR,runtimeException);
        return buildResponseEntity(apiErrorDto);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorDto apiErrorDto) {
        return new ResponseEntity<>(apiErrorDto, apiErrorDto.getStatus());
    }


}
