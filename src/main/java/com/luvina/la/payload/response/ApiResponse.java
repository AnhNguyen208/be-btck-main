/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse<T> {
    @Builder.Default
    String code = "200";

    Long totalRecords;
    ApiResponse<ErrorMessage> message;
    List<String> params;
    T departments;
    T employees;
}
