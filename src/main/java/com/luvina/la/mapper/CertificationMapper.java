/**
 * Copyright(C) 2024  Luvina
 * CertificationMapper.java, 10/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Chuyển đổi giữa CertificationDTO và Certification
 * @author AnhNLT
 */
@Mapper
public interface CertificationMapper {
    CertificationMapper MAPPER2 = Mappers.getMapper( CertificationMapper.class );

    /**
     * Chuyển đổi từ CertificationDTO sang Certification.
     * @param dto Đối tượng CertificationDTO cần chuyển đổi.
     * @return Đối tượng Certification đã chuyển đổi.
     */
    Certification toEntity(CertificationDTO dto);

    /**
     * Chuyển đổi từ Certification sang CertificationDTO.
     * @param entity Đối tượng Certification cần chuyển đổi.
     * @return Đối tượng CertificationDTO đã chuyển đổi.
     */
    CertificationDTO toDTO(Certification entity);

    /**
     * Chuyển đổi danh sách Certification sang danh sách CertificationDTO.
     * @param list Danh sách đối tượng Certification cần chuyển đổi.
     * @return Danh sách đối tượng CertificationDTO đã chuyển đổi.
     */
    Iterable<CertificationDTO> toList(Iterable<Certification> list);
}
