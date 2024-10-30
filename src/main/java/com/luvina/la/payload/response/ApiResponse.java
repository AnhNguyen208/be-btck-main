/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luvina.la.constant.CodeConstants;
import com.luvina.la.constant.ErrorConstants;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * Cấu trúc phản hồi dữ liệu của API
 * @param <T> Các kiểu dữ liệu trả về
 * @author AnhNLT
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse<T> {
    @Builder.Default
    String code = CodeConstants.SUCCESS.getCode();

    Long totalRecords;
    Long employeeId;
    String employeeName;
    Date employeeBirthDate;
    Long departmentId;
    String departmentName;
    String employeeEmail;
    String employeeTelephone;
    String employeeNameKana;
    String employeeLoginId;
    MessageResponse message;
    List<String> params;
    T departments;
    T employees;
    T certifications;

    /**
     * Tạo response cho ErrorCode
     * @param errorConstants thông tin error
     * @return response
     */
    public static ApiResponse<?> createMessageResponse(ErrorConstants errorConstants) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(errorConstants.getCode())
                .params(errorConstants.getParams())
                .build();

        return ApiResponse.builder()
                .code(CodeConstants.ERROR.getCode())
                .message(messageResponse)
                .build();
    }


    /**
     * Tạo response khi add, edit, delete employee
     * @param id employee id
     * @param code code message
     * @param params nội dung
     * @return response
     */
    public static ApiResponse<?> createMessageResponse(Long id, String code, List<String> params) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(code)
                .params(params)
                .build();

        return ApiResponse.builder()
                .employeeId(id)
                .code(CodeConstants.SUCCESS.getCode())
                .message(messageResponse)
                .build();
    }
}
