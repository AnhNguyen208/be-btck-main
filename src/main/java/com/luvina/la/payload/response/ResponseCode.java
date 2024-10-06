/**
 * Copyright(C) 2024  Luvina
 * ResponseCode.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.payload.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("200"),
    ERROR("500"),
    ;
    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }
}
