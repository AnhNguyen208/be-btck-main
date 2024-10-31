/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationMapper.java, 18/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.request.CertificationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

/**
 * Chuyển đổi giữa CertificationRequest và EmployeeCertification
 * @author AnhNLT
 */
@Mapper
public interface EmployeeCertificationMapper {
//    @Mapping(source = "request.certificationId", target = "certification", qualifiedByName = "setCertification")

    /**
     * Chuyển đổi CertificationRequest thành EmployeeCertification.
     * @param request Đối tượng CertificationRequest cần chuyển đổi.
     * @return Đối tượng EmployeeCertification sau khi chuyển đổi.
     */
    @Mapping(source = "certificationStartDate", target = "startDate", qualifiedByName = "setStartDate")
    @Mapping(source = "certificationEndDate", target = "endDate", qualifiedByName = "setEndDate")
    @Mapping(source = "employeeCertificationScore", target = "score")
    EmployeeCertification toEntity(CertificationRequest request);

    /**
     * Chuyển đổi danh sách CertificationRequest thành danh sách EmployeeCertification.
     * @param iterable Danh sách CertificationRequest cần chuyển đổi.
     * @return Danh sách EmployeeCertification sau khi chuyển đổi.
     */
    Iterable<EmployeeCertification> toList(Iterable<CertificationRequest> iterable);

    /**
     * Chuyển đổi chuỗi ngày bắt đầu thành đối tượng Date.
     * @param startDate Chuỗi ngày bắt đầu.
     * @return Đối tượng Date tương ứng.
     */
    @Named("setStartDate")
    static Date setStartDate(String startDate) {
        return new Date(startDate);
    }

    /**
     * Chuyển đổi chuỗi ngày kết thúc thành đối tượng Date.
     * @param endDate Chuỗi ngày kết thúc.
     * @return Đối tượng Date tương ứng.
     */
    @Named("setEndDate")
    static Date setEndDate(String endDate) {
        return new Date(endDate);
    }

}
