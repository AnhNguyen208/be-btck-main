/**
 * Copyright(C) 2024  Luvina
 * ErrorResponse.java, 07/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luvina.la.payload.ResponseCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Cấu trúc phản hồi của message response
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class MessageResponse {
    @Builder.Default
    String code = ResponseCode.ERROR.getCode();
    List<String> params;
}
