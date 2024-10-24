/**
 * Copyright(C) 2024  Luvina
 * ValidateRequest.java, 16/10/2024 AnhNLT
 */
package com.luvina.la.validate;

import com.luvina.la.exception.ErrorCode;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationRequest;
import com.luvina.la.payload.request.EditEmployeeRequest;
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
     * Kiểm tra thông tin employee khi add employee
     * @param request Thông tin employee
     */
    public ApiResponse<?> validateAddEmployeeRequest(AddEmployeeRequest request) {
        ApiResponse<?> response;

        response = validateEmployeeLoginIdAdd(request.getEmployeeLoginId());
        if (response != null) {
            return response;
        }

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

        response = validateEmployeeLoginPassword(request.getEmployeeLoginPassword());
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
     * Kiểm tra employeeId khi delete employee
     * @param employeeId employeeId cần validate
     */
    public ApiResponse<?> validateEmployeeId(Long employeeId) {
        ApiResponse<?> response = null;
        if (isNull(employeeId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_ID);
        } else if (!employeeService.checkExistsById(employeeId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER014_EMPLOYEE_ID);
        }

        return response;
    }

    /**
     * Kiểm tra thông tin employee khi edit
     * @param request Thông tin employee
     */
    public ApiResponse<?> validateEditEmployeeRequest(EditEmployeeRequest request) {
        ApiResponse<?> response;

        response = validateEmployeeIdEdit(request.getEmployeeId());
        if (response != null) {
            return response;
        }

        response = validateEmployeeLoginIdEdit(request.getEmployeeId() , request.getEmployeeLoginId());
        if (response != null) {
            return response;
        }

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

        if ("".compareTo(request.getEmployeeLoginPassword()) != 0) {
            response = validateEmployeeLoginPasswordEdit(request.getEmployeeLoginPassword());
            if (response != null) {
                return response;
            }
        }

        response = validateDepartmentId(request.getDepartmentId());
        if (response != null) {
            return response;
        }

        if (request.getCertifications() != null) {
            response = validateListCertifications(request.getCertifications());
            if (response != null) {
                return response;
            }
        }

        return null;
    }

    /**
     * Kiểm tra employeeId khi update employee
     * @param employeeId employeeId cần validate
     */
    private ApiResponse<?> validateEmployeeIdEdit(Long employeeId) {
        ApiResponse<?> response = null;

        if (isNull(employeeId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_ID);
        } else if (!employeeService.checkExistsById(employeeId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER013_EMPLOYEE_ID);
        }

        return response;
    }


    /**
     * Kiểm tra employeeLoginName khi
     * @param employeeLoginId Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeLoginIdAdd(String employeeLoginId) {
        ApiResponse<?> response = null;

        if (isNull(employeeLoginId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeLoginId, 50)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_LOGIN_ID);
        } else if (checkPattern(employeeLoginId, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER019_EMPLOYEE_LOGIN_ID);
        } else if (employeeService.checkExistsByEmployeeLoginIAdd(employeeLoginId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER003_EMPLOYEE_LOGIN_ID);
        }

        return response;
    }

    /**
     * Kiểm tra employeeLoginName
     * @param employeeLoginId Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeLoginIdEdit(Long employeeId, String employeeLoginId) {
        ApiResponse<?> response = null;

        if (isNull(employeeLoginId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeLoginId, 50)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_LOGIN_ID);
        } else if (checkPattern(employeeLoginId, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER019_EMPLOYEE_LOGIN_ID);
        } else if (employeeService.checkExistsByEmployeeLoginIEdit(employeeId, employeeLoginId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER003_EMPLOYEE_LOGIN_ID);
        }

        return response;
    }

    /**
     * Kiểm tra employeeName
     * @param employeeName Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeName(String employeeName) {
        ApiResponse<?> response = null;

        if (isNull(employeeName)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_NAME);
        } else if (checkMaxLength(employeeName, 125)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_NAME);
        }

        return response;
    }

    /**
     * Kiểm tra employeeNameKana
     * @param employeeNameKana Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeNameKana(String employeeNameKana) {
        ApiResponse<?> response = null;

        if (isNull(employeeNameKana)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_NAME_KANA);
        } else if (checkMaxLength(employeeNameKana, 125)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_NAME_KANA);
        } else if (checkPattern(employeeNameKana, "^[\\uFF65-\\uFF9F]+$")) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER009_EMPLOYEE_NAME_KANA);
        }

        return response;
    }

    /**
     * Kiểm tra employeeBirthDate
     * @param employeeBirthDate Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeBirthDate(String employeeBirthDate) {
        ApiResponse<?> response = null;

        if (isNull(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_BIRTHDATE);
        } else if (checkValueDate(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER011_EMPLOYEE_BIRTHDATE);
        } else if (checkFormatDate(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER005_EMPLOYEE_BIRTHDATE);
        }

        return response;
    }

    /**
     * Kiểm tra employeeEmail
     * @param employeeEmail Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeEmail(String employeeEmail) {
        ApiResponse<?> response = null;

        if (isNull(employeeEmail)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_EMAIL);
        } else if (checkMaxLength(employeeEmail, 125)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_EMAIL);
        }

        return response;
    }

    /**
     * Kiểm tra employeeTelephone
     * @param employeeTelephone Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeTelephone(String employeeTelephone) {
        ApiResponse<?> response = null;

        if (isNull(employeeTelephone)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_TELEPHONE);
        } else if (checkMaxLength(employeeTelephone, 50)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER006_EMPLOYEE_TELEPHONE);
        } else if (checkPattern(employeeTelephone, "[0-9]+")) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER008_EMPLOYEE_TELEPHONE);
        }

        return response;
    }

    /**
     * Kiểm tra employeeLoginPassword khi add employee
     * @param employeeLoginPassword Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeLoginPassword(String employeeLoginPassword) {
        ApiResponse<?> response = null;

        if (isNull(employeeLoginPassword)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_EMPLOYEE_LOGIN_PASSWORD);
        } else if (!checkLength(employeeLoginPassword, 8, 50)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER007_EMPLOYEE_LOGIN_PASSWORD);
        }

        return response;
    }

    /**
     * Kiểm tra employeeLoginPassword khi edit employee
     * @param employeeLoginPassword Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEmployeeLoginPasswordEdit(String employeeLoginPassword) {
        ApiResponse<?> response = null;

        if (!checkLength(employeeLoginPassword, 8, 50)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER007_EMPLOYEE_LOGIN_PASSWORD);
        }

        return response;
    }

    /**
     * Kiểm tra departmentId
     * @param departmentId Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateDepartmentId(Long departmentId) {
        ApiResponse<?> response = null;

        if (isNull(departmentId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER002_DEPARTMENT_ID);
        } else if (departmentId <= 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER018_DEPARTMENT_ID);
        } else if (!departmentService.checkExistsById(departmentId)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER004_DEPARTMENT_ID);
        }

        return response;
    }

    /**
     * Kiểm tra List CertificationRequest
     * @param requests Giá trị cần kiểm tra
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
     */
    private ApiResponse<?> validateStartDate(String startDate) {
        ApiResponse<?> response = null;

        if (isNull(startDate) || checkValueDate(startDate)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_CERTIFICATION_START_DATE);
        } else if (checkFormatDate(startDate)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER005_CERTIFICATION_START_DATE);
        }

        return response;
    }

    /**
     * Kiểm tra endDate
     * @param startDateStr Giá trị cần kiểm tra
     * @param endDateStr Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateEndDate(String startDateStr, String endDateStr) {
        ApiResponse<?> response = null;

        Date startDate = new Date(startDateStr);
        Date endDate = new Date(endDateStr);

        if (isNull(endDateStr) || checkValueDate(endDateStr)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_CERTIFICATION_START_DATE);
        } else if (checkFormatDate(endDateStr)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER005_CERTIFICATION_START_DATE);
        } else if ((endDate).compareTo(startDate) <= 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER012_CERTIFICATION_START_DATE_END_DATE);
        }

        return response;
    }

    /**
     * Kiểm tra score
     * @param score Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateScore(BigDecimal score) {
        ApiResponse<?> response = null;

        if (isNull(score)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_CERTIFICATION_SCORE);
        } else if (Integer.parseInt(String.valueOf(score)) <= 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER018_CERTIFICATION_SCORE);
        }

        return response;
    }

    /**
     * Kiểm tra certificationId
     * @param id Giá trị cần kiểm tra
     */
    private ApiResponse<?> validateCertificationId(Long id) {
        ApiResponse<?> response = null;

        if (isNull(id)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER001_CERTIFICATION_ID);
        } else if (id <= 0) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER018_CERTIFICATION_ID);
        } else if (!certificationService.checkExistsById(id)) {
            response = ApiResponse.createMessageResponse(ErrorCode.ER004_CERTIFICATION_ID);
        }

        return response;
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
