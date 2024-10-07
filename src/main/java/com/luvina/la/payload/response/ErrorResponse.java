/**
 * Copyright(C) 2024  Luvina
 * ErrorResponse.java, 07/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Chuẩn hóa cấu trúc phản hồi cho các API
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ErrorResponse {
    @Builder.Default
    String code = ResponseCode.ERROR.getCode();
    List<String> params;
}
