/**
 * Copyright(C) 2024  Luvina
 * ValidateRequest.java, 16/10/2024 AnhNLT
 */
package com.luvina.la.validate;

import com.luvina.la.constant.ErrorConstants;
import com.luvina.la.constant.ParamOrderByConstants;
import com.luvina.la.payload.request.CertificationRequest;
import com.luvina.la.payload.request.EmployeeRequest;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.service.CertificationService;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Kiểm tra dữ liệu nhân từ FE và trả về 1 response
 * @author AnhNLT
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidateRequest {
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    EmployeeService employeeService;
    DepartmentService departmentService;
    CertificationService certificationService;

    /**
     * Kiểm tra tính hợp lệ của các tham số khi tìm kiếm employee
     *
     * @param ordEmployeeName hướng sắp xếp tên nhân viên (ASC hoặc DESC)
     * @param ordCertificationName hướng sắp xếp tên chứng chỉ (ASC hoặc DESC)
     * @param ordEndDate hướng sắp xếp ngày kết thúc (ASC hoặc DESC)
     * @param offset  vị trí bắt đầu tìm kiếm
     * @param limit số lượng bản ghi sẽ lấy ra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    public ApiResponse<?> validateSearchParams(
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offset,
            String limit) {
        ErrorConstants code = null;

        if (checkOrdValue(ordEmployeeName) || checkOrdValue(ordCertificationName) || checkOrdValue((ordEndDate))) {
            // Kiểm tra ordEmployeeName, ordCertificationName, ordEndDate chỉ nhận giá trị ASC hoặc DESC chưa
            code = ErrorConstants.ER021_ORDER;
        } else if (Integer.parseInt(limit) < 0) {
            // Kiểm tra limit có phải số nguyên dương không
            code = ErrorConstants.ER018_LIMIT;
        } else if (Integer.parseInt(offset) < 0) {
            // Kiểm tra offset có phải số nguyên dương không
            code = ErrorConstants.ER018_OFFSET;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra thông tin employee khi thêm mới employee
     * @param request Thông tin employee cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    public ApiResponse<?> validateAddEmployeeRequest(EmployeeRequest request) {
        ApiResponse<?> response;

        response = validateEmployeeLoginIdAdd(request.getEmployeeLoginId());
        if (response != null) {
            return response;
        }

        response = validateEmployeeLoginPassword(request.getEmployeeLoginPassword());
        if (response != null) {
            return response;
        }

        response = validateEmployeeRequest(request);
        return response;
    }

    /**
     * Kiểm tra employeeId khi xóa employee
     * @param employeeId employeeId cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    public ApiResponse<?> validateDeleteEmployeeRequest(Long employeeId) {
        ErrorConstants code = null;

        if (isNull(employeeId)) {
            code = ErrorConstants.ER001_EMPLOYEE_ID;
        } else if (!employeeService.checkExistsById(employeeId)) {
            code = ErrorConstants.ER014_EMPLOYEE_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeId khi lấy thông tin chi tiết của employee
     * @param employeeId employeeId cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    public ApiResponse<?> validateGetDetailEmployeeRequest(Long employeeId) {
        ErrorConstants code = null;

        if (isNull(employeeId)) {
            code = ErrorConstants.ER001_EMPLOYEE_ID;
        } else if (!employeeService.checkExistsById(employeeId)) {
            code = ErrorConstants.ER013_EMPLOYEE_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra thông tin employee khi chỉnh sửa employee
     * @param request Thông tin employee cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    public ApiResponse<?> validateEditEmployeeRequest(EmployeeRequest request) {
        ApiResponse<?> response;

        response = validateEmployeeIdEdit(request.getEmployeeId());
        if (response != null) {
            return response;
        }

        response = validateEmployeeLoginIdEdit(request.getEmployeeId() , request.getEmployeeLoginId());
        if (response != null) {
            return response;
        }

        if ("".compareTo(request.getEmployeeLoginPassword()) != 0) {
            response = validateEmployeeLoginPasswordEdit(request.getEmployeeLoginPassword());
            return response;
        }

        response = validateEmployeeRequest(request);
        return response;
    }

    /**
     * Kiểm tra thông tin employee
     * @param request Thông tin employee cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeRequest(EmployeeRequest request) {
        ApiResponse<?> response;

        response = validateEmployeeName(request.getEmployeeName());
        if (response != null) {
            return response;
        }

        response = validateEmployeeNameKana(request.getEmployeeNameKana());
        if (response != null) {
            return response;
        }

        response = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        if (response != null) {
            return response;
        }

        response = validateEmployeeEmail(request.getEmployeeEmail());
        if (response != null) {
            return response;
        }

        response = validateEmployeeTelephone(request.getEmployeeTelephone());
        if (response != null) {
            return response;
        }

        response = validateDepartmentId(request.getDepartmentId());
        if (response != null) {
            return response;
        }

        if (request.getCertifications() != null ) {
            response = validateListCertifications(request.getCertifications());
            return response;
        }

        return null;
    }

    /**
     * Kiểm tra giá trị Order by
     * @param value gía trị cần kiểm tra
     * @return
     *         true: Giá trị hợp lệ
     *         false: Giá trị không hợp lệ
     */
    private boolean checkOrdValue(String value) {
        return (!ParamOrderByConstants.ASC.getValue().equals(value)) && !ParamOrderByConstants.DESC.getValue().equals(value);
    }

    /**
     * Kiểm tra employeeId khi chỉnh sửa employee
     * @param employeeId employeeId cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeIdEdit(Long employeeId) {
        ErrorConstants code = null;

        if (isNull(employeeId)) {
            code = ErrorConstants.ER001_EMPLOYEE_ID;
        } else if (!employeeService.checkExistsById(employeeId)) {
            code = ErrorConstants.ER013_EMPLOYEE_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }


    /**
     * Kiểm tra employeeLoginName khi thêm mới employee
     * @param employeeLoginId Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeLoginIdAdd(String employeeLoginId) {
        ErrorConstants code = null;

        if (isNull(employeeLoginId)) {
            code = ErrorConstants.ER001_EMPLOYEE_LOGIN_ID;
        } else if (checkMaxLength(employeeLoginId, 50)) {
            code = ErrorConstants.ER006_EMPLOYEE_LOGIN_ID;
        } else if (checkPattern(employeeLoginId, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            code = ErrorConstants.ER019_EMPLOYEE_LOGIN_ID;
        } else if (employeeService.checkExistsByEmployeeLoginIAdd(employeeLoginId)) {
            code = ErrorConstants.ER003_EMPLOYEE_LOGIN_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeLoginName
     * @param employeeLoginId Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeLoginIdEdit(Long employeeId, String employeeLoginId) {
        ErrorConstants code = null;

        if (isNull(employeeLoginId)) {
            code = ErrorConstants.ER001_EMPLOYEE_LOGIN_ID;
        } else if (checkMaxLength(employeeLoginId, 50)) {
            code = ErrorConstants.ER006_EMPLOYEE_LOGIN_ID;
        } else if (checkPattern(employeeLoginId, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            code = ErrorConstants.ER019_EMPLOYEE_LOGIN_ID;
        } else if (employeeService.checkExistsByEmployeeLoginIEdit(employeeId, employeeLoginId)) {
            code = ErrorConstants.ER003_EMPLOYEE_LOGIN_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeName
     * @param employeeName Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeName(String employeeName) {
        ErrorConstants code = null;

        if (isNull(employeeName)) {
            code = ErrorConstants.ER001_EMPLOYEE_NAME;
        } else if (checkMaxLength(employeeName, 125)) {
            code = ErrorConstants.ER006_EMPLOYEE_NAME;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeNameKana
     * @param employeeNameKana Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeNameKana(String employeeNameKana) {
        ErrorConstants code = null;

        if (isNull(employeeNameKana)) {
            code = ErrorConstants.ER001_EMPLOYEE_NAME_KANA;
        } else if (checkMaxLength(employeeNameKana, 125)) {
            code = ErrorConstants.ER006_EMPLOYEE_NAME_KANA;
        } else if (checkPattern(employeeNameKana, "^[\\uFF65-\\uFF9F]+$")) {
            code = ErrorConstants.ER009_EMPLOYEE_NAME_KANA;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeBirthDate
     * @param employeeBirthDate Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeBirthDate(String employeeBirthDate) {
        ErrorConstants code = null;

        if (isNull(employeeBirthDate)) {
            code = ErrorConstants.ER001_EMPLOYEE_BIRTHDATE;
        } else if (checkValueDate(employeeBirthDate)) {
            code = ErrorConstants.ER011_EMPLOYEE_BIRTHDATE;
        } else if (checkFormatDate(employeeBirthDate)) {
            code = ErrorConstants.ER005_EMPLOYEE_BIRTHDATE;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeEmail
     * @param employeeEmail Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeEmail(String employeeEmail) {
        ErrorConstants code = null;

        if (isNull(employeeEmail)) {
            code = ErrorConstants.ER001_EMPLOYEE_EMAIL;
        } else if (checkMaxLength(employeeEmail, 125)) {
            code = ErrorConstants.ER006_EMPLOYEE_EMAIL;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeTelephone
     * @param employeeTelephone Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeTelephone(String employeeTelephone) {
        ErrorConstants code = null;

        if (isNull(employeeTelephone)) {
            code = ErrorConstants.ER001_EMPLOYEE_TELEPHONE;
        } else if (checkMaxLength(employeeTelephone, 50)) {
            code = ErrorConstants.ER006_EMPLOYEE_TELEPHONE;
        } else if (checkPattern(employeeTelephone, "[0-9]+")) {
            code = ErrorConstants.ER008_EMPLOYEE_TELEPHONE;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeLoginPassword khi thêm mới employee
     * @param employeeLoginPassword Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeLoginPassword(String employeeLoginPassword) {
        ErrorConstants code = null;

        if (isNull(employeeLoginPassword)) {
            code = ErrorConstants.ER001_EMPLOYEE_LOGIN_PASSWORD;
        } else if (!checkLength(employeeLoginPassword, 8, 50)) {
            code = ErrorConstants.ER007_EMPLOYEE_LOGIN_PASSWORD;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra employeeLoginPassword khi chỉnh sửa employee
     * @param employeeLoginPassword Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEmployeeLoginPasswordEdit(String employeeLoginPassword) {
        ErrorConstants code = null;

        if (!checkLength(employeeLoginPassword, 8, 50)) {
            code = ErrorConstants.ER007_EMPLOYEE_LOGIN_PASSWORD;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra departmentId
     * @param departmentId Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateDepartmentId(Long departmentId) {
        ErrorConstants code = null;

        if (isNull(departmentId)) {
            code = ErrorConstants.ER002_DEPARTMENT_ID;
        } else if (departmentId <= 0) {
            code = ErrorConstants.ER018_DEPARTMENT_ID;
        } else if (!departmentService.checkExistsById(departmentId)) {
            code = ErrorConstants.ER004_DEPARTMENT_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra danh sách certification
     * @param requests Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateListCertifications(List<CertificationRequest> requests) {
        ApiResponse<?> response;

        for (CertificationRequest certification: requests) {
            response = validateStartDate(certification.getCertificationStartDate());
            if (response != null) {
                return response;
            }

            response = validateEndDate(certification.getCertificationStartDate(),
                    certification.getCertificationEndDate());
            if (response != null) {
                return response;
            }

            response = validateScore(certification.getEmployeeCertificationScore());
            if (response != null) {
                return response;
            }

            response = validateCertificationId(certification.getCertificationId());
            if (response != null) {
                return response;
            }
        }

        return null;
    }

    /**
     * Kiểm tra startDate
     * @param startDate Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateStartDate(String startDate) {
        ErrorConstants code = null;

        if (isNull(startDate) || checkValueDate(startDate)) {
            code = ErrorConstants.ER001_CERTIFICATION_START_DATE;
        } else if (checkFormatDate(startDate)) {
            code = ErrorConstants.ER005_CERTIFICATION_START_DATE;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra endDate
     * @param startDateStr Giá trị cần kiểm tra
     * @param endDateStr Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateEndDate(String startDateStr, String endDateStr) {
        ErrorConstants code = null;

        Date startDate = new Date(startDateStr);
        Date endDate = new Date(endDateStr);

        if (isNull(endDateStr) || checkValueDate(endDateStr)) {
            code = ErrorConstants.ER001_CERTIFICATION_START_DATE;
        } else if (checkFormatDate(endDateStr)) {
            code = ErrorConstants.ER005_CERTIFICATION_START_DATE;
        } else if ((endDate).compareTo(startDate) <= 0) {
            code = ErrorConstants.ER012_CERTIFICATION_START_DATE_END_DATE;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra score
     * @param score Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateScore(BigDecimal score) {
        ErrorConstants code = null;

        if (isNull(score)) {
            code = ErrorConstants.ER001_CERTIFICATION_SCORE;
        } else if (Integer.parseInt(String.valueOf(score)) <= 0) {
            code = ErrorConstants.ER018_CERTIFICATION_SCORE;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra certificationId
     * @param id Giá trị cần kiểm tra
     * @return
     *      Hợp lệ trả về null
     *      Không hợp lệ trả về ApiResponse chứa thông báo lỗi tương ứng
     */
    private ApiResponse<?> validateCertificationId(Long id) {
        ErrorConstants code = null;

        if (isNull(id)) {
            code = ErrorConstants.ER001_CERTIFICATION_ID;
        } else if (id <= 0) {
            code = ErrorConstants.ER018_CERTIFICATION_ID;
        } else if (!certificationService.checkExistsById(id)) {
            code = ErrorConstants.ER004_CERTIFICATION_ID;
        }

        return code == null ? null : ApiResponse.createMessageResponse(code);
    }

    /**
     * Kiểm tra đối tượng có null không
     * @param obj Giá trị cần kiểm tra
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Kiểm tra độ dài xâu
     * @param obj Giá trị cần kiểm tra
     * @param length Độ dài
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean checkMaxLength(Object obj, int length) {
        return obj.toString().length() > length;
    }

    /**
     * Kiểm tra định dạng xâu
     * @param obj Giá trị cần kiểm tra
     * @param regex Định dạng cần kiểm tra
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean checkPattern(Object obj, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return !pattern.matcher(obj.toString()).matches();
    }

    /**
     * Kiểm tra giá trị ngày có hợp lệ không
     * @param obj Giá trị cần kiểm tra
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean checkValueDate(Object obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = sdf.parse(obj.toString());
            return date == null;
        } catch (ParseException e) {
            return true;
        }
    }

    /**
     * Kiểm tra định dạng ngày
     * @param obj Giá trị cần kiểm tra
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean checkFormatDate(Object obj) {
       return !obj.toString().matches("\\d{4}/\\d{2}/\\d{2}");
    }

    /**
     * Kiểm tra độ dài
     * @param obj Giá trị cần kiểm tra
     * @param min Độ dài min
     * @param max Độ dài max
     * @return
     *      true: Hợp lệ
     *      false: Không hợp lệ
     */
    private boolean checkLength(Object obj, int min, int max) {
        return min <= obj.toString().length() && obj.toString().length() <= max;
    }
}
