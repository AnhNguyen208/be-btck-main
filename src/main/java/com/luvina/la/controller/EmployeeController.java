/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.constant.ParamOrderByConstants;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.exception.ErrorCode;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.EditEmployeeRequest;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.validate.ValidateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @return
     *  Trường hợp thành công: Danh sách EmployeeDTO
     *  Trường hợp lỗi
     *      {
     *          "code": "500"
     *          "message":  {
     * 	            "code": "ER015"
     * 	            "params": []
     *          }
     *      }
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
        ApiResponse<?> response;

        if (checkOrdValue(ordEmployeeName) || checkOrdValue(ordCertificationName) || checkOrdValue((ordEndDate))) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER021_ORDER);
        } else if (Integer.parseInt(limit) < 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER018_LIMIT);
        } else if (Integer.parseInt(offset) < 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER018_OFFSET);
        } else {
            Long totalRecords = employeeService.countEmployees(employeeName, departmentId);
            List<EmployeeDTO> list = employeeService.getEmployees(employeeName, departmentId,
                    ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

            response = ApiResponse.<List<EmployeeDTO>>builder()
                    .totalRecords(totalRecords)
                    .employees(list)
                    .build();

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Api thêm mới employee
     * @param request Thông tin employee nhận từ FE
     * @return
     *  Trường hợp thành công
     *      {
     *          "code": "200"
     *          "employeeId": "1",
     *          "message":  {
     * 	            "code": "MSG001"
     * 	            "params": []
     *          }
     *      }
     *  Trường hợp lỗi
     *      {
     *          "code": "500"
     *          "message":  {
     * 	            "code": "ER015"
     * 	            "params": []
     *          }
     *      }
     */
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeRequest request) {
        ApiResponse<?> response = validateRequest.validateAddEmployeeRequest(request);

        if (response == null) {
            Long id = employeeService.addEmployee(request);
            response = ApiResponse.createMessageResponse(id, "MSG001", List.of(new String[]{}));
//            response = ApiResponse.createMessageResponse(1L, "MSG001", new ArrayList<>(List.of(new String[]{})));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Api lấy thông tin chi tiết của employee
     * @param employeeId EmployeeId nhận từ FE
     * @return
     *  Trường hợp thành công: Thông tin chi tiết của employee
     *  Trường hợp lỗi
     *      {
     *          "code": "500"
     *          "message":  {
     * 	            "code": "ER013"
     * 	            "params": []
     *          }
     *      }
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getDetailEmployee(@PathVariable Long employeeId) {
        ApiResponse<?> response;
        response = validateRequest.validateEmployeeIdGet(employeeId);

        if (response == null) {
            EmployeeDetailDTO detailDTO = employeeService.getDetailEmployee(employeeId);

            response = ApiResponse.builder()
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
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Api xóa employee
     * @param employeeId EmployeeId nhận từ FE
     * @return
     *  Trường hợp thành công
     *      {
     *          "code": "200"
     *          "employeeId": "1",
     *          "message":  {
     * 	           "code": "MSG003"
     * 	            "params": []
     *           }
     *       }
     *  Trường hợp lỗi
     *      {
     *          "code": "500"
     *          "employeeId": "1",
     *          "message":  {
     * 	            "code": "ER015"
     * 	            "params": []
     *          }
     *       }
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        ApiResponse<?> response = validateRequest.validateEmployeeIdDelete(employeeId);
        if (response == null) {
            employeeService.deleteEmployee(employeeId);

            response = ApiResponse.createMessageResponse(employeeId, "MSG003", List.of(new String[]{}));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Api chỉnh sửa employee
     * @param request Thông tin employee nhận từ FE
     * @return
     *  Trường hợp thành công
     *      {
     *          "code": "200"
     *          "employeeId": "1",
     *          "message":  {
     * 	            "code": "MSG002"
     * 	            "params": []
     *          }
     *      }
     *  Trường hợp lỗi
     *      {
     *          "code": "500"
     *          "message":  {
     * 	            "code": "ER015"
     * 	            "params": []
     *          }
     *      }
     */
    @PutMapping
    public ResponseEntity<?> editEmployee(@RequestBody EditEmployeeRequest request) {
        ApiResponse<?> response = validateRequest.validateEditEmployeeRequest(request);
        if (response == null) {
            Long employeeId = employeeService.editEmployee(request);

            response = ApiResponse.createMessageResponse(employeeId, "MSG002", List.of(new String[]{}));
        }

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
