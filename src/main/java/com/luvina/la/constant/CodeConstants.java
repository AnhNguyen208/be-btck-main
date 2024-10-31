/**
 * Copyright(C) 2024  Luvina
 * CodeConstants.java, 18/10/2024 AnhNLT
 */
package com.luvina.la.constant;

import lombok.Getter;

/**
 * Enum lưu trữ các mã trạng thái phản hồi.
 * @author AnhNLT
 */
@Getter
public enum CodeConstants {
    SUCCESS("200"),
    ERROR("500"),
    MSG_001("MSG001"),
    MSG_002("MSG002"),
    MSG_003("MSG003"),
    ;
    private final String code;

    CodeConstants(String code) {
        this.code = code;
    }
}
