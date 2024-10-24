/**
 * Copyright(C) 2024  Luvina
 * EmployeeMapper.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeCertificationDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.EditEmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Chuyển đổi giữa EmployeeDTO và Employee
 * use:
 *  EmployeeMapper.MAPPER.toEntity(dto);
 *  EmployeeMapper.MAPPER.toList(list);
 *  @author AnhNLT
 */
@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper( EmployeeMapper.class );

    Employee toEntity(EmployeeDTO dto);

    @Mapping(source = "employeeBirthDate", target = "employeeBirthDate", qualifiedByName = "setBirthDate")
    Employee toEntityAdd(AddEmployeeRequest request);

    @Mapping(source = "employeeBirthDate", target = "employeeBirthDate", qualifiedByName = "setBirthDate")
    Employee toEntityEdit(EditEmployeeRequest request);

    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "certifications", target = "certificationName", qualifiedByName = "getFirstCertificationName")
    @Mapping(source = "certifications", target = "endDate", qualifiedByName = "getFirstEndDate")
    @Mapping(source = "certifications", target = "score", qualifiedByName = "getFirstScore")
    EmployeeDTO toDto(Employee entity);

    Iterable<EmployeeDTO> toList(Iterable<Employee> list);

    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "certifications", target = "certifications", qualifiedByName = "getCertifications")
    EmployeeDetailDTO toDetailDto(Employee entity);

    @Named("getFirstCertificationName")
    static String getFirstCertificationName(List<EmployeeCertification> certifications) {
        if (certifications != null && certifications.get(0) != null && certifications.get(0).getCertification() != null) {
            return certifications.get(0).getCertification().getCertificationName();
        } else {
            return null;
        }
    }
    @Named("getFirstEndDate")
    static Date getFirstEndDate(List<EmployeeCertification> certifications) {
        if (certifications != null) {
            return certifications.get(0).getEndDate();
        } else {
            return null;
        }
    }
    @Named("getFirstScore")
    static BigDecimal getFirstScore(List<EmployeeCertification> certifications) {
        if (certifications != null) {
            return certifications.get(0).getScore();
        } else {
            return null;
        }
    }

    @Named("setBirthDate")
    static Date setBirthDate(String employeeBirthDate) {
        return new Date(employeeBirthDate);
    }

    @Named("getCertifications")
    static List<EmployeeCertificationDTO> getCertifications(List<EmployeeCertification> certifications) {
        List<EmployeeCertificationDTO> dtos = new ArrayList<>();

        for(EmployeeCertification certification: certifications) {
            EmployeeCertificationDTO dto = EmployeeCertificationDTO.builder()
                    .certificationId(certification.getCertification().getCertificationId())
                    .certificationName(certification.getCertification().getCertificationName())
                    .startDate(certification.getStartDate())
                    .endDate(certification.getEndDate())
                    .score(certification.getScore())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
