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
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Cấu trúc phản hồi của message response
 * @author AnhNLT
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class MessageResponse {
    @Builder.Default
    String code = HttpStatus.OK.toString();
    List<String> params;
}
