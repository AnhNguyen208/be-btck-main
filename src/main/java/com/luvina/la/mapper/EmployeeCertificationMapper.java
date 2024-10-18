package com.luvina.la.mapper;

import com.luvina.la.entity.Certification;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.request.CertificationRequest;
import com.luvina.la.repository.CertificationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;
import java.util.List;

@Mapper
public interface EmployeeCertificationMapper {
//    @Mapping(source = "request.certificationId", target = "certification", qualifiedByName = "setCertification")
    @Mapping(source = "request.certificationStartDate", target = "startDate", qualifiedByName = "setStartDate")
    @Mapping(source = "request.certificationStartDate", target = "endDate", qualifiedByName = "setEndDate")
    @Mapping(source = "request.employeeCertificationScore", target = "score")
    EmployeeCertification toEntity(CertificationRequest request);

    Iterable<EmployeeCertification> toList(Iterable<CertificationRequest> iterable);

    @Named("setStartDate")
    static Date setStartDate(String startDate) {
        return new Date(startDate);
    }

    @Named("setEndDate")
    static Date setEndDate(String endDate) {
        return new Date(endDate);
    }

}
