/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.constant.ParamOrderByConstants;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.exception.AppException;
import com.luvina.la.exception.ErrorCode;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.validate.ValidateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller để quản lý employee
 * @author AnhNLT
 */
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class EmployeeController {
    EmployeeService employeeService;
    ValidateRequest validateRequest;

    /**
     * Api lấy danh sách employee
     * @param employeeName Tên nhân viên
     * @param departmentId Mã phòng ban
     * @param ordEmployeeName ASC hoặc DESC
     * @param ordCertificationName ASC hoặc DESC
     * @param ordEndDate ASC hoặc DESC
     * @param offset Số nguyên dương
     * @param limit Số nguyên dương
     * @return Danh sách EmployeeDTO
     *
     */
    @GetMapping
    public ResponseEntity<?> getListEmployees(
            @RequestParam(name = "employee_name", required = false) String employeeName,
            @RequestParam(name = "department_id", required = false) String departmentId,
            @RequestParam(name = "ord_employee_name", required = false) String ordEmployeeName,
            @RequestParam(name = "ord_certification_name", required = false) String ordCertificationName,
            @RequestParam(name = "ord_end_date",required = false) String ordEndDate,
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "5") String limit
    ) {
        if(checkOrdValue(ordEmployeeName) || checkOrdValue(ordCertificationName) || checkOrdValue((ordEndDate))) {
            throw new AppException(ErrorCode.ER021_ORDER);
        } else if(Integer.parseInt(limit) < 0) {
            throw new AppException(ErrorCode.ER018_LIMIT);
        } else if(Integer.parseInt(offset) < 0) {
            throw new AppException(ErrorCode.ER018_OFFSET);
        } else {
            Long totalRecords = employeeService.countEmployees(employeeName, departmentId);
            List<EmployeeDTO> list = employeeService.getEmployees(employeeName, departmentId,
                    ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

            ApiResponse<List<EmployeeDTO>> response = ApiResponse.<List<EmployeeDTO>>builder()
                    .totalRecords(totalRecords)
                    .employees(list)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Api thêm mới employee
     * @param request Thông tin employee nhận từ FE
     * @return ID employee vừa thêm
     */
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeRequest request) {
        validateRequest.validateAddEmployeeRequest(request);
        Long id = employeeService.addEmployee(request);
        ApiResponse<?> response = ApiResponse.createMessageResponse(id, "MSG001", new ArrayList<>(List.of(new String[]{})));
//        ApiResponse<?> response = ApiResponse.createMessageResponse(0L, "MSG001", new ArrayList<>(List.of(new String[]{})));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getDetailEmployee(@PathVariable Long employeeId) {
        EmployeeDetailDTO detailDTO = employeeService.getDetailEmployee(employeeId);

        ApiResponse<?> response = ApiResponse.builder()
                .employeeId(detailDTO.getEmployeeId())
                .employeeName(detailDTO.getEmployeeName())
                .employeeBirthDate(detailDTO.getEmployeeBirthDate())
                .departmentId(detailDTO.getDepartmentId())
                .departmentName(detailDTO.getDepartmentName())
                .employeeEmail(detailDTO.getEmployeeEmail())
                .employeeTelephone(detailDTO.getEmployeeTelephone())
                .employeeNameKana(detailDTO.getEmployeeNameKana())
                .employeeLoginId(detailDTO.getEmployeeLoginId())
                .certifications(detailDTO.getCertifications())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Kiểm tra giá trị Order by
     * @param value gía trị cần kiểm tra
     * @return
     *         true: Giá tr hợp lệ
     *         false: Giá trị không hợp lệ
     */
    private boolean checkOrdValue(String value) {
        return (!ParamOrderByConstants.ASC.getValue().equals(value)) && !ParamOrderByConstants.DESC.getValue().equals(value);
    }
}
