/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.constant.CodeConstants;
import com.luvina.la.constant.ErrorConstants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.EditEmployeeRequest;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.validate.ValidateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu HTTP liên quan đến employee
 * Bao gồm các chức năng lấy danh sách, lấy thông tin chi tiết, thêm mới, chỉnh sửa, và xóa employee.
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
     * Lấy danh sách employee từ EmployeeService
     *
     * @param employeeName Tên nhân viên
     * @param departmentId Mã phòng ban
     * @param ordEmployeeName ASC hoặc DESC
     * @param ordCertificationName ASC hoặc DESC
     * @param ordEndDate ASC hoặc DESC
     * @param offset Số nguyên dương
     * @param limit Số nguyên dương
     * @return
     *  Trường hợp thành công
     *  {
     *     "code": "200"
     *     "totalRecords": 2,
     *     "employees": [
     *       {
     *         "employeeId": "1",
     *         "employeeName": "Nguyễn Văn A",
     *         "employeeBirthDate": "1983/01/01",
     *         "departmentName": "Phòng DevN",
     *         "employeeEmail": "nguyenvana@luvina.net",
     *         "employeeTelephone": "01234567",
     *         "certificationName": "Trình độ tiếng Nhật cấp 1",
     *         "endDate": "9999/12/31",
     *         "score": "999"
     *       },
     *       {
     *         "employeeId": "2",
     *         "employeeName": "Nguyễn Văn B",
     *         "employeeBirthDate": "1983/01/02",
     *         "departmentName": "Phòng DevN",
     *         "employeeEmail": "nguyenvanb@luvina.net",
     *         "employeeTelephone": "01234568",
     *         "certificationName": "Trình độ tiếng Nhật cấp 2",
     *         "endDate": "9999/12/31",
     *         "score": "999"
     *       }
     *     ]
     *  }
     *  Trường hợp lỗi
     *  {
     *     "code": "500"
     *     "message":  {
     * 	    "code": "ER015"
     * 	    "params": []         				
     *        }
     *  }
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
        try {
            ApiResponse<?> response = validateRequest.validateSearchParams(
                    ordEmployeeName, ordCertificationName, ordEndDate, offset, limit
            );

            if (response == null) {
                // Lấy tổng số bản ghi phù hợp từ service
                Long totalRecords = employeeService.countEmployees(employeeName, departmentId);

                // Lấy danh sách employee từ service
                List<EmployeeDTO> list = employeeService.getEmployees(employeeName, departmentId,
                        ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

                //Tạo response
                response = ApiResponse.<List<EmployeeDTO>>builder()
                        .totalRecords(totalRecords)
                        .employees(list)
                        .build();

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Thêm mới employee qua EmployeeService
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
        try {
            // Kiểm tra thông tin employee từ FE có hợp lệ không
            ApiResponse<?> response = validateRequest.validateAddEmployeeRequest(request);

            if (response == null) {
                // Lưu thông tin employee
                Long id = employeeService.addEmployee(request);

                // Tạo response
                response = ApiResponse.createMessageResponse(id, CodeConstants.MSG_001.getCode(), List.of(new String[]{}));
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Lấy thông tin của employee từ EmployeeService
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
        try {
            // Kiểm tra employeeId nhận từ FE có hợp lệ không
            ApiResponse<?> response = validateRequest.validateGetDetailEmployeeRequest(employeeId);

            if (response == null) {
                // Lấy thông tin chi tiết employee từ service
                EmployeeDetailDTO detailDTO = employeeService.getDetailEmployee(employeeId);

                // Tạo response
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
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
        try {
            // Kiểm tra employeeId nhận từ FE có hợp lệ không
            ApiResponse<?> response = validateRequest.validateDeleteEmployeeRequest(employeeId);

            if (response == null) {
                // Xóa employee
                employeeService.deleteEmployee(employeeId);

                // Tạo response
                response = ApiResponse.createMessageResponse(employeeId, CodeConstants.MSG_003.getCode(), List.of(new String[]{}));
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
        try {
            // Kiểm tra thông tin employee từ FE có hợp lệ không
            ApiResponse<?> response = validateRequest.validateEditEmployeeRequest(request);

            if (response == null) {
                // Chỉnh sửa employee
                Long employeeId = employeeService.editEmployee(request);

                // Tạo response
                response = ApiResponse.createMessageResponse(employeeId, CodeConstants.MSG_002.getCode(), List.of(new String[]{}));
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
