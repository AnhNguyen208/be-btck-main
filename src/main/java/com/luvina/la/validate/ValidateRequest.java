package com.luvina.la.validate;

import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationRequest;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidateRequest {
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    EmployeeService employeeService;
    DepartmentService departmentService;
    public ApiResponse<?> validateAddEmployeeRequest(AddEmployeeRequest request) {
        System.out.println(request);
        ApiResponse<?> response;
        if(validateEmployeeLoginName(request.getEmployeeLoginId()) != null) {
            response = validateEmployeeLoginName(request.getEmployeeLoginId());
        } else if (validateEmployeeName(request.getEmployeeName()) != null) {
            response = validateEmployeeName(request.getEmployeeName());
        } else if (validateEmployeeNameKana(request.getEmployeeNameKana()) != null) {
            response = validateEmployeeNameKana(request.getEmployeeNameKana());
        } else if (validateEmployeeBirthDate(request.getEmployeeBirthDate()) != null) {
            response = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        } else if (validateEmployeeEmail(request.getEmployeeEmail()) != null) {
            response = validateEmployeeEmail(request.getEmployeeEmail());
        } else if (validateEmployeeTelephone(request.getEmployeeTelephone()) != null) {
            response = validateEmployeeTelephone(request.getEmployeeTelephone());
        } else if (validateEmployeeLoginPassword(request.getEmployeeLoginPassword()) != null) {
            response = validateEmployeeLoginPassword(request.getEmployeeLoginPassword());
        } else if (validateDepartmentId(request.getDepartmentId()) != null) {
            response = validateDepartmentId(request.getDepartmentId());
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeLoginName(String employeeLoginName) {
        ApiResponse<?> response;
        if (isNull(employeeLoginName)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeLoginName, 50)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER006_EMPLOYEE_LOGIN_ID);
        } else if (checkPattern(employeeLoginName, "^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER019_EMPLOYEE_LOGIN_ID);
        } else if (employeeService.checkExistsByEmployeeLoginId(employeeLoginName)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER003_EMPLOYEE_LOGIN_ID);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeName(String employeeName) {
        ApiResponse<?> response;
        if (isNull(employeeName)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_NAME);
        } else if (checkMaxLength(employeeName, 125)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER006_EMPLOYEE_NAME);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeNameKana(String employeeNameKana) {
        ApiResponse<?> response;
        if (isNull(employeeNameKana)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_LOGIN_ID);
        } else if (checkMaxLength(employeeNameKana, 125)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER006_EMPLOYEE_NAME_KANA);
        } else if (checkPattern(employeeNameKana, "^[\\u30A0-\\u30FF・]+$")) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER009_EMPLOYEE_NAME_KANA);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeBirthDate(String employeeBirthDate) {
        ApiResponse<?> response;
        if (isNull(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_BIRTHDATE);
        } else if (!checkValueDate(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER011_EMPLOYEE_BIRTHDATE);
        } else if (!checkFormatDate(employeeBirthDate)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER005_EMPLOYEE_BIRTHDATE);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeEmail(String employeeEmail) {
        ApiResponse<?> response;
        if(isNull(employeeEmail)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_EMAIL);
        } else if (checkMaxLength(employeeEmail, 125)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER006_EMPLOYEE_EMAIL);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeTelephone(String employeeTelephone) {
        ApiResponse<?> response;
        if(isNull(employeeTelephone)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_TELEPHONE);
        } else if (checkMaxLength(employeeTelephone, 50)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER006_EMPLOYEE_TELEPHONE);
        } else if (checkPattern(employeeTelephone, "/^[\\x00-\\x7F]*$/")) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER008_EMPLOYEE_TELEPHONE);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateEmployeeLoginPassword(String employeeLoginPassword) {
        ApiResponse<?> response;
        if(isNull(employeeLoginPassword)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER001_EMPLOYEE_LOGIN_PASSWORD);
        } else if (!checkLength(employeeLoginPassword, 8, 50)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER007_EMPLOYEE_LOGIN_PASSWORD);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateDepartmentId(Long departmentId) {
        ApiResponse<?> response;
        if (isNull(departmentId)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER002_DEPARTMENT_ID);
        } else if (departmentId <= 0) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER018_DEPARTMENT_ID);
        } else if (!departmentService.checkExistsById(departmentId)) {
            response = ApiResponse.createMessageResponse(ErrorMessage.ER004_DEPARTMENT_ID);
        } else {
            response = null;
        }

        return response;
    }

    private ApiResponse<?> validateListCertifications(List<CertificationRequest> requests) {
        ApiResponse<?> response = null;
        for (CertificationRequest request: requests) {

        }
        return null;
    }

    private ApiResponse<?> validateCertification(CertificationRequest request) {
        ApiResponse<?> response = null;

        return null;
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    private boolean checkMaxLength(Object obj, int length) {
        return obj.toString().length() > length;
    }

    private boolean checkPattern(Object obj, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return !pattern.matcher(obj.toString()).matches();
    }

    private boolean checkValueDate(Object obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = sdf.parse(obj.toString());
            System.out.println(date);
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean checkFormatDate(Object obj) {
       return obj.toString().matches("\\d{4}/\\d{2}/\\d{2}");
    }

    private boolean checkLength(Object obj, int min, int max) {
        return min <= obj.toString().length() && obj.toString().length() <= max;
    }
}
