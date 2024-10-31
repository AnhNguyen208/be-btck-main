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
import com.luvina.la.payload.request.EmployeeRequest;
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

    /**
     * Chuyển đổi EmployeeDTO sang Employee.
     * @param dto đối tượng EmployeeDTO.
     * @return đối tượng Employee.
     */
    Employee toEntity(EmployeeDTO dto);

    /**
     * Chuyển đổi EmployeeRequest sang Employee.
     * @param request thông tin yêu cầu thêm nhân viên.
     * @return đối tượng Employee.
     */
    @Mapping(source = "employeeBirthDate", target = "employeeBirthDate", qualifiedByName = "setBirthDate")
    Employee requestToEntity(EmployeeRequest request);

    /**
     * Chuyển đổi Employee sang EmployeeDTO.
     * @param entity đối tượng Employee.
     * @return đối tượng EmployeeDTO.
     */
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "certifications", target = "certificationName", qualifiedByName = "getFirstCertificationName")
    @Mapping(source = "certifications", target = "endDate", qualifiedByName = "getFirstEndDate")
    @Mapping(source = "certifications", target = "score", qualifiedByName = "getFirstScore")
    EmployeeDTO toDto(Employee entity);

    /**
     * Chuyển đổi danh sách Employee sang danh sách EmployeeDTO.
     * @param list danh sách đối tượng Employee.
     * @return danh sách đối tượng EmployeeDTO.
     */
    Iterable<EmployeeDTO> toList(Iterable<Employee> list);

    /**
     * Chuyển đổi Employee sang EmployeeDetailDTO.
     * @param entity đối tượng Employee.
     * @return đối tượng EmployeeDetailDTO.
     */
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "certifications", target = "certifications", qualifiedByName = "getCertifications")
    EmployeeDetailDTO toDetailDto(Employee entity);

    /**
     * Lấy tên chứng chỉ đầu tiên từ danh sách chứng chỉ.
     * @param certifications danh sách EmployeeCertification.
     * @return tên chứng chỉ đầu tiên hoặc null.
     */
    @Named("getFirstCertificationName")
    static String getFirstCertificationName(List<EmployeeCertification> certifications) {
        if (certifications != null && certifications.get(0) != null && certifications.get(0).getCertification() != null) {
            return certifications.get(0).getCertification().getCertificationName();
        } else {
            return null;
        }
    }

    /**
     * Lấy ngày kết thúc đầu tiên từ danh sách chứng chỉ.
     * @param certifications danh sách EmployeeCertification.
     * @return ngày kết thúc đầu tiên hoặc null.
     */
    @Named("getFirstEndDate")
    static Date getFirstEndDate(List<EmployeeCertification> certifications) {
        if (certifications != null) {
            return certifications.get(0).getEndDate();
        } else {
            return null;
        }
    }

    /**
     * Lấy điểm số đầu tiên từ danh sách chứng chỉ.
     * @param certifications danh sách EmployeeCertification.
     * @return điểm số đầu tiên hoặc null.
     */
    @Named("getFirstScore")
    static BigDecimal getFirstScore(List<EmployeeCertification> certifications) {
        if (certifications != null) {
            return certifications.get(0).getScore();
        } else {
            return null;
        }
    }

    /**
     * Chuyển đổi chuỗi ngày sinh thành đối tượng Date.
     * @param employeeBirthDate chuỗi ngày sinh.
     * @return đối tượng Date.
     */
    @Named("setBirthDate")
    static Date setBirthDate(String employeeBirthDate) {
        return new Date(employeeBirthDate);
    }

    /**
     * Chuyển đổi danh sách EmployeeCertification sang danh sách EmployeeCertificationDTO.
     * @param certifications danh sách EmployeeCertification.
     * @return danh sách EmployeeCertificationDTO.
     */
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
