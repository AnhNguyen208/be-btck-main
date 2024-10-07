/**
 * Copyright(C) 2024  Luvina
 * ParamConstants.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.payload;

import lombok.Getter;

/**
 * Quản lý các hằng số
 */
@Getter
public enum ParamConstants {
    ASC("ASC"),
    DESC("DESC")
    ;

    private final String value;

    ParamConstants(String value) {
        this.value = value;
    }
}
