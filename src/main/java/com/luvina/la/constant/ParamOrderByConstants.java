/**
 * Copyright(C) 2024  Luvina
 * ParamConstants.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.constant;

import lombok.Getter;

/**
 * Enum lưu trữ các tham số săp xếp.
 * @author AnhNLT
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
