/**
 * Copyright(C) 2024  Luvina
 * ValidateRequest.java, 16/10/2024 AnhNLT
 */
package com.luvina.la.validate;

import com.luvina.la.exception.AppException;
import com.luvina.la.exception.ErrorCode;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationRequest;
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
 * Validate dữ liệu nhân từ FE
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
     * Validate thông tin employee
     * @param request Thông tin employee
     */
    public void validateAddEmployeeRequest(AddEmployeeRequest request) {
        validateEmployeeLoginName(request.getEmployeeLoginId());
        validateEmployeeName(request.getEmployeeName());
        validateEmployeeNameKana(request.getEmployeeNameKana());
        validateEmployeeBirthDate(request.getEmployeeBirthDate());
        validateEmployeeEmail(request.getEmployeeEmail());
        validateEmployeeTelephone(request.getEmployeeTelephone());
        validateEmployeeLoginPassword(request.getEmployeeLoginPassword());
        validateDepartmentId(request.getDepartmentId());
        validateListCertifications(request.getCertifications());
    }

    /**
     * Validate employeeLoginName
     * @param employeeLoginName Giá trị cần validate
     */
    private void validateEmployeeLoginName(String employeeLoginName) {
        if (isNull(employeeLoginName)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeLoginName, 50)) {
            throw new AppException(ErrorCode.ER006_EMPLOYEE_LOGIN_ID);
        } else if (checkPattern(employeeLoginName, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            throw new AppException(ErrorCode.ER019_EMPLOYEE_LOGIN_ID);
        } else if (employeeService.checkExistsByEmployeeLoginId(employeeLoginName)) {
            throw new AppException(ErrorCode.ER003_EMPLOYEE_LOGIN_ID);
        }
    }

    /**
     * Validate employeeName
     * @param employeeName Giá trị cần validate
     */
    private void validateEmployeeName(String employeeName) {
        if (isNull(employeeName)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_NAME);
        } else if (checkMaxLength(employeeName, 125)) {
            throw new AppException(ErrorCode.ER006_EMPLOYEE_NAME);
        }
    }

    /**
     * Validate employeeNameKana
     * @param employeeNameKana Giá trị cần validate
     */
    private void validateEmployeeNameKana(String employeeNameKana) {
        if (isNull(employeeNameKana)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeNameKana, 125)) {
            throw new AppException(ErrorCode.ER006_EMPLOYEE_NAME_KANA);
        } else if (checkPattern(employeeNameKana, "^[\\u30A0-\\u30FF・]+$")) {
            throw new AppException(ErrorCode.ER009_EMPLOYEE_NAME_KANA);
        }
    }

    /**
     * Validate employeeBirthDate
     * @param employeeBirthDate Giá trị cần validate
     */
    private void validateEmployeeBirthDate(String employeeBirthDate) {
        if (isNull(employeeBirthDate)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_BIRTHDATE);
        } else if (checkValueDate(employeeBirthDate)) {
            throw new AppException(ErrorCode.ER011_EMPLOYEE_BIRTHDATE);
        } else if (checkFormatDate(employeeBirthDate)) {
            throw new AppException(ErrorCode.ER005_EMPLOYEE_BIRTHDATE);
        }
    }

    /**
     * Validate employeeEmail
     * @param employeeEmail Giá trị cần validate
     */
    private void validateEmployeeEmail(String employeeEmail) {
        if(isNull(employeeEmail)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_EMAIL);
        } else if (checkMaxLength(employeeEmail, 125)) {
            throw new AppException(ErrorCode.ER006_EMPLOYEE_EMAIL);
        }
    }

    /**
     * Validate employeeTelephone
     * @param employeeTelephone Giá trị cần validate
     */
    private void validateEmployeeTelephone(String employeeTelephone) {
        if(isNull(employeeTelephone)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_TELEPHONE);
        } else if (checkMaxLength(employeeTelephone, 50)) {
            throw new AppException(ErrorCode.ER006_EMPLOYEE_TELEPHONE);
        } else if (checkPattern(employeeTelephone, "[0-9]+")) {
            throw new AppException(ErrorCode.ER008_EMPLOYEE_TELEPHONE);
        }
    }

    /**
     * Validate employeeLoginPassword
     * @param employeeLoginPassword Giá trị cần validate
     */
    private void validateEmployeeLoginPassword(String employeeLoginPassword) {
        if(isNull(employeeLoginPassword)) {
            throw new AppException(ErrorCode.ER001_EMPLOYEE_LOGIN_PASSWORD);
        } else if (!checkLength(employeeLoginPassword, 8, 50)) {
            throw new AppException(ErrorCode.ER007_EMPLOYEE_LOGIN_PASSWORD);
        }
    }

    /**
     * Validate departmentId
     * @param departmentId Giá trị cần validate
     */
    private void validateDepartmentId(Long departmentId) {
        if (isNull(departmentId)) {
            throw new AppException(ErrorCode.ER002_DEPARTMENT_ID);
        } else if (departmentId <= 0) {
            throw new AppException(ErrorCode.ER018_DEPARTMENT_ID);
        } else if (!departmentService.checkExistsById(departmentId)) {
            throw new AppException(ErrorCode.ER004_DEPARTMENT_ID);
        }
    }

    /**
     * Validate List CertificationRequest
     * @param requests Giá trị cần validate
     */
    private void validateListCertifications(List<CertificationRequest> requests) {
        for (CertificationRequest certification: requests) {
            validateStartDate(certification.getCertificationStartDate());
            validateEndDate(certification.getCertificationStartDate(),
                    certification.getCertificationEndDate());
            validateScore(certification.getEmployeeCertificationScore());
            validateCertificationId(certification.getCertificationId());
        }
    }

    /**
     * Validate startDate
     * @param startDate Giá trị cần validate
     */
    private void validateStartDate(String startDate) {
        if (isNull(startDate) || checkValueDate(startDate)) {
            throw new AppException(ErrorCode.ER001_CERTIFICATION_START_DATE);
        } else if (checkFormatDate(startDate)) {
            throw new AppException(ErrorCode.ER005_CERTIFICATION_START_DATE);
        }
    }

    /**
     * Validate endDate
     * @param startDateStr Giá trị cần validate
     * @param endDateStr Giá trị cần validate
     */
    private void validateEndDate(String startDateStr, String endDateStr) {
        Date startDate = new Date(startDateStr);
        Date endDate = new Date(endDateStr);

        if (isNull(endDateStr) || checkValueDate(endDateStr)) {
            throw new AppException(ErrorCode.ER001_CERTIFICATION_START_DATE);
        } else if (checkFormatDate(endDateStr)) {
            throw new AppException(ErrorCode.ER005_CERTIFICATION_START_DATE);
        } else if ((endDate).compareTo(startDate) <= 0) {
            throw new AppException(ErrorCode.ER012_CERTIFICATION_START_DATE_END_DATE);
        }
    }

    /**
     * Validate score
     * @param score Giá trị cần validate
     */
    private void validateScore(BigDecimal score) {
        if(isNull(score)) {
            throw new AppException(ErrorCode.ER001_CERTIFICATION_SCORE);
        } else if (Integer.parseInt(String.valueOf(score)) <= 0) {
            throw new AppException(ErrorCode.ER018_CERTIFICATION_SCORE);
        }
    }

    /**
     * Validate certificationId
     * @param id Giá trị cần validate
     */
    private void validateCertificationId(Long id) {
        if(isNull(id)) {
            throw new AppException(ErrorCode.ER001_CERTIFICATION_ID);
        } else if (id <= 0) {
            throw new AppException(ErrorCode.ER018_CERTIFICATION_ID);
        } else if (certificationService.checkExistsById(id)) {
            throw new AppException(ErrorCode.ER004_CERTIFICATION_ID);
        }
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
