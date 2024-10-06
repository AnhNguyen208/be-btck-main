package com.luvina.la.payload;

import lombok.Getter;

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
