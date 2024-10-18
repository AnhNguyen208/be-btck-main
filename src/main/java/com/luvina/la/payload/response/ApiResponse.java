/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luvina.la.constant.CodeConstants;
import com.luvina.la.exception.AppException;
import com.luvina.la.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

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
    String code = CodeConstants.SUCCESS.getCode();

    Long totalRecords;
    T employeeId;
    MessageResponse message;
    List<String> params;
    T departments;
    T employees;
    T certifications;

    public static ApiResponse<?> createMessageResponse(AppException exception) {
        MessageResponse errorResponse = MessageResponse.builder()
                .code(exception.getErrorCode().getCode())
                .params(exception.getErrorCode().getParams())
                .build();

        return ApiResponse.builder()
                .code(CodeConstants.ERROR.getCode())
                .message(errorResponse)
                .build();
    }

    public static ApiResponse<?> createMessageResponse(Long id, String code, List<String> params) {
        MessageResponse errorResponse = MessageResponse.builder()
                .code(code)
                .params(params)
                .build();

        return ApiResponse.builder()
                .employeeId(id)
                .code(CodeConstants.SUCCESS.getCode())
                .message(errorResponse)
                .build();
    }
}
