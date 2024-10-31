/**
 * Copyright(C) 2024  Luvina
 * DepartmentMapper.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Chuyển đổi giữa DepartmentDTO và Department
 * @author AnhNLT
 */
@Mapper
public interface DepartmentMapper {
    DepartmentMapper MAPPER1 = Mappers.getMapper( DepartmentMapper.class );

    /**
     * Chuyển đổi từ DepartmentDTO sang Department.
     * @param dto Đối tượng DepartmentDTO cần chuyển đổi.
     * @return Đối tượng Department sau khi chuyển đổi.
     */
    Department toEntity(DepartmentDTO dto);

    /**
     * Chuyển đổi từ Department sang DepartmentDTO.
     * @param entity Đối tượng Department cần chuyển đổi.
     * @return Đối tượng DepartmentDTO sau khi chuyển đổi.
     */
    DepartmentDTO toDTO(Department entity);

    /**
     * Chuyển đổi danh sách Department sang danh sách DepartmentDTO.
     * @param list Danh sách các đối tượng Department cần chuyển đổi.
     * @return Danh sách đối tượng DepartmentDTO sau khi chuyển đổi.
     */
    Iterable<DepartmentDTO> toList(Iterable<Department> list);
}
