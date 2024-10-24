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
 */
@Mapper
public interface CertificationMapper {
    CertificationMapper MAPPER2 = Mappers.getMapper( CertificationMapper.class );

    Certification toEntity(CertificationDTO dto);
    CertificationDTO toDTO(Certification entity);
    Iterable<CertificationDTO> toList(Iterable<Certification> list);
}
