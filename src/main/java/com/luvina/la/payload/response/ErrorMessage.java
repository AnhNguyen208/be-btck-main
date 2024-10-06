/**
 * Copyright(C) 2024  Luvina
 * ErrorMessage.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorMessage {
    DEPARTMENT_ERROR("ER023", new ArrayList<>(List.of(new String[]{}))),
    INVALID_ORDER_PARAMETER("ER021", new ArrayList<>(List.of(new String[]{}))),
    INVALID_OFFSET_PARAMETER("ER018", new ArrayList<>(List.of(new String[]{"オフセット"}))),
    INVALID_LIMIT_PARAMETER("ER018", new ArrayList<>(List.of(new String[]{"リミット"}))),
            ;
    private final String code;
    private final List<String> params;

    ErrorMessage(String code, List<String> params) {
        this.code = code;
        this.params = params;
    }
}
