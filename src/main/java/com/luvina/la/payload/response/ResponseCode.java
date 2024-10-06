package com.luvina.la.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
