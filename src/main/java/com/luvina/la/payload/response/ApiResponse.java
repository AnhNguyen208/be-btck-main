/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.payload.ResponseCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Cấu trúc phản hồi dữ liệu của API
 * @param <T> Các kiểu dữ liệu trả về
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse<T> {
    @Builder.Default
    String code = ResponseCode.SUCCESS.getCode();

    Long totalRecords;
    ErrorResponse message;
    List<String> params;
    T departments;
    T employees;

    public static ApiResponse<?> createErrorResponse(ErrorMessage errorMessage) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorMessage.getCode())
                .params(errorMessage.getParams())
                .build();

        return ApiResponse.builder()
                .code(ResponseCode.ERROR.getCode())
                .message(errorResponse)
                .build();
    }
}
