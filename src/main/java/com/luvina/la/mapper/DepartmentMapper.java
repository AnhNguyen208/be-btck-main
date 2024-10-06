/**
 * Copyright(C) 2024  Luvina
 * DepartmentMapper.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper MAPPER1 = Mappers.getMapper( DepartmentMapper.class );

    Department toEntity(DepartmentDTO dto);
    DepartmentDTO toDTO(Department entity);
    Iterable<DepartmentDTO> toList(Iterable<Department> list);
}
