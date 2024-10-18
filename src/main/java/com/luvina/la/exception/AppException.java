/**
 * Copyright(C) 2024  Luvina
 * AppException.java, 18/10/2024 AnhNLT
 */
package com.luvina.la.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Quản lý các đối tượng với thông điệp lỗi tương ứng
 */
@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}
