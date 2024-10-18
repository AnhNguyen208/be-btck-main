/**
 * Copyright(C) 2024  Luvina
 * ParamConstants.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.constant;

import lombok.Getter;

/**
 * Quản lý các hằng số
 */
@Getter
public enum ParamOrderByConstants {
    ASC("ASC"),
    DESC("DESC")
    ;

    private final String value;

    ParamOrderByConstants(String value) {
        this.value = value;
    }
}
