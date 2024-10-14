/**
 * Copyright(C) 2024  Luvina
 * ErrorMessage.java, 06/10/2024 AnhNLT
 */
package com.luvina.la.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Quản lý các thông điệp lỗi
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorMessage {
    ER001_EMPLOYEE_LOGIN_ID("ER001", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER001_EMPLOYEE_NAME("ER001", new ArrayList<>(List.of(new String[]{"氏名"}))),
    ER001_EMPLOYEE_NAME_KANA("ER001", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),
    ER001_EMPLOYEE_BIRTHDATE("ER001", new ArrayList<>(List.of(new String[]{"生年月日"}))),
    ER001_EMPLOYEE_EMAIL("ER001", new ArrayList<>(List.of(new String[]{"メールアドレス"}))),
    ER001_EMPLOYEE_TELEPHONE("ER001", new ArrayList<>(List.of(new String[]{"電話番号"}))),
    ER001_EMPLOYEE_LOGIN_PASSWORD("ER001", new ArrayList<>(List.of(new String[]{"パスワード"}))),
    ER001_CERTIFICATION_START_DATE("ER001", new ArrayList<>(List.of(new String[]{"資格交付日"}))),
    ER001_CERTIFICATION_END_DATE("ER001", new ArrayList<>(List.of(new String[]{"失効日"}))),
    ER001_CERTIFICATION_SCORE("ER001", new ArrayList<>(List.of(new String[]{"点数"}))),
    ER001_CERTIFICATION_ID("ER001", new ArrayList<>(List.of(new String[]{"資格"}))),
    ER002_DEPARTMENT_ID("ER002", new ArrayList<>(List.of(new String[]{"グループ"}))),
    ER003_EMPLOYEE_LOGIN_ID("ER003", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER004_DEPARTMENT_ID("ER004", new ArrayList<>(List.of(new String[]{"グループ"}))),
    ER004_CERTIFICATION_ID("ER004", new ArrayList<>(List.of(new String[]{"資格"}))),
    ER005_EMPLOYEE_BIRTHDATE("ER005", new ArrayList<>(List.of(new String[]{"生年月日", "yyyy/MM/dd"}))),
    ER005_CERTIFICATION_START_DATE("ER005", new ArrayList<>(List.of(new String[]{ "資格交付日", "yyyy/MM/dd"}))),
    ER005_CERTIFICATION_END_DATE("ER005", new ArrayList<>(List.of(new String[]{ "失効日", "yyyy/MM/dd"}))),
    ER006_EMPLOYEE_LOGIN_ID("ER006", new ArrayList<>(List.of(new String[]{"アカウント名"}))),
    ER006_EMPLOYEE_NAME("ER006", new ArrayList<>(List.of(new String[]{"氏名"}))),
    ER006_EMPLOYEE_NAME_KANA("ER006", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),
    ER006_EMPLOYEE_EMAIL("ER006", new ArrayList<>(List.of(new String[]{"メールアドレス"}))),
    ER006_EMPLOYEE_TELEPHONE("ER006", new ArrayList<>(List.of(new String[]{"電話番号"}))),
    ER007_EMPLOYEE_LOGIN_PASSWORD("ER007", new ArrayList<>(List.of(new String[]{"パスワード", "8", "50"}))),
    ER008_EMPLOYEE_TELEPHONE("ER008", new ArrayList<>(List.of(new String[]{"電話番号"}))),
    ER009_EMPLOYEE_NAME_KANA("ER009", new ArrayList<>(List.of(new String[]{"カタカナ氏名"}))),
    ER011_EMPLOYEE_BIRTHDATE("ER011", new ArrayList<>(List.of(new String[]{"生年月日"}))),
    ER012_CERTIFICATION_START_DATE_END_DATE("ER012", new ArrayList<>(List.of(new String[]{}))),
    ER018_OFFSET("ER018", new ArrayList<>(List.of(new String[]{"オフセット"}))),
    ER018_LIMIT("ER018", new ArrayList<>(List.of(new String[]{"リミット"}))),
    ER018_DEPARTMENT_ID("ER018", new ArrayList<>(List.of(new String[]{"グループ"}))),
    ER018_CERTIFICATION_SCORE("ER018", new ArrayList<>(List.of(new String[]{"点数"}))),
    ER018_CERTIFICATION_ID("ER018", new ArrayList<>(List.of(new String[]{"資格"}))),
    ER019_EMPLOYEE_LOGIN_ID("ER019", new ArrayList<>(List.of(new String[]{}))),
    ER021_ORDER("ER021", new ArrayList<>(List.of(new String[]{}))),
    ER023("ER023", new ArrayList<>(List.of(new String[]{}))),
             ;
    private final String code;
    private final List<String> params;

    ErrorMessage(String code, List<String> params) {
        this.code = code;
        this.params = params;
    }
}
