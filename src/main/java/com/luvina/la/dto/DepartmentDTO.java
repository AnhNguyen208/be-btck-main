package com.luvina.la.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentDTO implements Serializable {
    static final long serialVersionUID = -1269191683062086843L;

    Long departmentId;
    String departmentName;
}
