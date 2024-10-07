/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Chuẩn hóa cấu trúc phản hồi cho các API
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

    public static ApiResponse<?> ErrorMessageResponse(ErrorMessage errorMessage) {
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
