/**
 * Copyright(C) 2024  Luvina
 * GlobalExceptionHandler.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.exception;

import com.luvina.la.payload.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Xử lý các thông điệp lỗi
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingRuntimeException(Exception exception) {
        ApiResponse<?> response = ApiResponse.createMessageResponse(new AppException(ErrorCode.ER023));
        System.out.println(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        ApiResponse<?> response = ApiResponse.createMessageResponse(exception);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
