/**
 * Copyright(C) 2024  Luvina
 * ErrorMessage.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Quản lý các thông điệp lỗi
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorCode {
    // 1.1 Validate parameter [employeeLoginId]
    ER001_EMPLOYEE_LOGIN_ID("ER001", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER003_EMPLOYEE_LOGIN_ID("ER003", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER006_EMPLOYEE_LOGIN_ID("ER006", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER019_EMPLOYEE_LOGIN_ID("ER019", new ArrayList<>(List.of(new String[]{}))),

    // 1.2 Validate parameter [employeeName]
    ER001_EMPLOYEE_NAME("ER001", new ArrayList<>(List.of(new String[]{"氏名"}))),
    ER006_EMPLOYEE_NAME("ER006", new ArrayList<>(List.of(new String[]{"氏名"}))),

    // 1.3 Validate parameter [employeeNameKana]
    ER001_EMPLOYEE_NAME_KANA("ER001", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),
    ER006_EMPLOYEE_NAME_KANA("ER006", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),
    ER009_EMPLOYEE_NAME_KANA("ER009", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),

    // 1.4 Validate parameter [employeeBirthDate]
    ER001_EMPLOYEE_BIRTHDATE("ER001", new ArrayList<>(List.of(new String[]{"生年月日"}))),
    ER005_EMPLOYEE_BIRTHDATE("ER005", new ArrayList<>(List.of(new String[]{"生年月日", "yyyy/MM/dd"}))),
    ER011_EMPLOYEE_BIRTHDATE("ER011", new ArrayList<>(List.of(new String[]{"生年月日"}))),

    // 1.5 Validate parameter [employeeEmail]
    ER001_EMPLOYEE_EMAIL("ER001", new ArrayList<>(List.of(new String[]{"メールアドレス"}))),
    ER006_EMPLOYEE_EMAIL("ER006", new ArrayList<>(List.of(new String[]{"メールアドレス"}))),

    // 1.6 Validate parameter [employeeTelephone]
    ER001_EMPLOYEE_TELEPHONE("ER001", new ArrayList<>(List.of(new String[]{"電話番号"}))),
    ER006_EMPLOYEE_TELEPHONE("ER006", new ArrayList<>(List.of(new String[]{"電話番号"}))),
    ER008_EMPLOYEE_TELEPHONE("ER008", new ArrayList<>(List.of(new String[]{"電話番号"}))),

    // 1.7 Validate parameter [employeeLoginPassword]
    ER001_EMPLOYEE_LOGIN_PASSWORD("ER001", new ArrayList<>(List.of(new String[]{"パスワード"}))),
    ER007_EMPLOYEE_LOGIN_PASSWORD("ER007", new ArrayList<>(List.of(new String[]{"パスワード", "8", "50"}))),

    // 1.8 Validate parameter [departmentId]
    ER002_DEPARTMENT_ID("ER002", new ArrayList<>(List.of(new String[]{"グループ"}))),
    ER004_DEPARTMENT_ID("ER004", new ArrayList<>(List.of(new String[]{"グループ"}))),
    ER018_DEPARTMENT_ID("ER018", new ArrayList<>(List.of(new String[]{"グループ"}))),

    // 1.9 Validate parameter [certifications]
    ER001_CERTIFICATION_START_DATE("ER001", new ArrayList<>(List.of(new String[]{"資格交付日"}))),
    ER001_CERTIFICATION_END_DATE("ER001", new ArrayList<>(List.of(new String[]{"失効日"}))),
    ER001_CERTIFICATION_SCORE("ER001", new ArrayList<>(List.of(new String[]{"点数"}))),
    ER001_CERTIFICATION_ID("ER001", new ArrayList<>(List.of(new String[]{"資格"}))),
    ER004_CERTIFICATION_ID("ER004", new ArrayList<>(List.of(new String[]{"資格"}))),
    ER005_CERTIFICATION_START_DATE("ER005", new ArrayList<>(List.of(new String[]{ "資格交付日", "yyyy/MM/dd"}))),
    ER005_CERTIFICATION_END_DATE("ER005", new ArrayList<>(List.of(new String[]{ "失効日", "yyyy/MM/dd"}))),
    ER012_CERTIFICATION_START_DATE_END_DATE("ER012", new ArrayList<>(List.of(new String[]{}))),
    ER018_CERTIFICATION_SCORE("ER018", new ArrayList<>(List.of(new String[]{"点数"}))),
    ER018_CERTIFICATION_ID("ER018", new ArrayList<>(List.of(new String[]{"資格"}))),

    // 1.10 Validate parameter [employeeId]
    ER001_EMPLOYEE_ID("ER001", new ArrayList<>(List.of(new String[]{"ID"}))),
    ER014_EMPLOYEE_ID("ER014", new ArrayList<>(List.of(new String[]{"ID"}))),

    // Validate parameter list employee
    ER018_OFFSET("ER018", new ArrayList<>(List.of(new String[]{"オフセット"}))),
    ER018_LIMIT("ER018", new ArrayList<>(List.of(new String[]{"リミット"}))),
    ER021_ORDER("ER021", new ArrayList<>(List.of(new String[]{}))),

    // System error
    ER023("ER023", new ArrayList<>(List.of(new String[]{}))),
             ;
    private final String code;
    private final List<String> params;

    ErrorCode(String code, List<String> params) {
        this.code = code;
        this.params = params;
    }
}
