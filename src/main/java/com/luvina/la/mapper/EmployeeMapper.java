package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * use:
 *  EmployeeMapper.MAPPER.toEntity(dto);
 *  EmployeeMapper.MAPPER.toList(list);
 */
@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper( EmployeeMapper.class );

    Employee toEntity(EmployeeDTO entity);
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "employeeCertificationList", target = "certificationName", qualifiedByName = "getFirstCertificationName")
    @Mapping(source = "employeeCertificationList", target = "endDate", qualifiedByName = "getFirstEndDate")
    @Mapping(source = "employeeCertificationList", target = "score", qualifiedByName = "getFirstScore")
    EmployeeDTO toDto(Employee entity);

    Iterable<EmployeeDTO> toList(Iterable<Employee> list);

    @Named("getFirstCertificationName")
    public static String getFirstCertificationName(List<EmployeeCertification> employeeCertificationList) {
        return employeeCertificationList.get(0).getCertification().getCertificationName();
    }
    @Named("getFirstEndDate")
    public static Date getFirstEndDate(List<EmployeeCertification> employeeCertificationList) {
        return employeeCertificationList.get(0).getEndDate();
    }
    @Named("getFirstScore")
    public static BigDecimal getFirstScore(List<EmployeeCertification> employeeCertificationList) {
        return employeeCertificationList.get(0).getScore();
    }
}
