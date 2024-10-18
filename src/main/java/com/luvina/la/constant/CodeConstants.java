/**
 * Copyright(C) 2024  Luvina
 * CodeConstants.java, 18/10/2024 AnhNLT
 */
package com.luvina.la.constant;

import lombok.Getter;
@Getter
public enum CodeConstants {
    SUCCESS("200"),
    ERROR("500"),
    ;
    private final String code;

    CodeConstants(String code) {
        this.code = code;
    }
}
