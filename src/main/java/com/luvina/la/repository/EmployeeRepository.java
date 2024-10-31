/**
 * Copyright(C) 2024  Luvina
 * EmployeeRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Các thao tác liên quan đến thực thể Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Tìm kiếm nhân viên theo employeeLoginId.
     *
     * @param employeeLoginId ID đăng nhập của nhân viên
     * @return Optional chứa Employee nếu tìm thấy, ngược lại là rỗng
     */
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);

    /**
     * Tìm kiếm danh sách nhân viên theo các tiêu chí tìm kiếm và sắp xếp động.
     *
     * @param employeeName Tên nhân viên cần tìm (hoặc một phần)
     * @param departmentId ID phòng ban của nhân viên
     * @param ordEmployeeName Thứ tự sắp xếp tên nhân viên (ASC/DESC)
     * @param ordCertificationName Thứ tự sắp xếp tên chứng chỉ (ASC/DESC)
     * @param ordEndDate Thứ tự sắp xếp ngày kết thúc chứng chỉ (ASC/DESC)
     * @param pageable Đối tượng phân trang và sắp xếp
     * @return Danh sách EmployeeDTO chứa các nhân viên thỏa mãn điều kiện tìm kiếm
     */
    @Query("SELECT new com.luvina.la.dto.EmployeeDTO(" +
            "e.employeeId, " +
            "e.employeeName, " +
            "e.employeeBirthDate, " +
            "d.departmentName, " +
            "e.employeeEmail, " +
            "e.employeeTelephone, " +
            "c.certificationName, " +
            "ec.endDate, " +
            "ec.score) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.certifications ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "AND (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%')) " +
            "ORDER BY " +
            "CASE WHEN :ordEmployeeName IS NOT NULL AND :ordEmployeeName = 'ASC' THEN e.employeeName END ASC, " +
            "CASE WHEN :ordEmployeeName IS NOT NULL AND :ordEmployeeName = 'DESC' THEN e.employeeName END DESC, " +
            "CASE WHEN :ordCertificationName IS NOT NULL AND :ordCertificationName = 'ASC' THEN c.certificationName END ASC, " +
            "CASE WHEN :ordCertificationName IS NOT NULL AND :ordCertificationName = 'DESC' THEN c.certificationName END DESC, " +
            "CASE WHEN :ordEndDate IS NOT NULL AND :ordEndDate = 'ASC' THEN ec.endDate END ASC, " +
            "CASE WHEN :ordEndDate IS NOT NULL AND :ordEndDate = 'DESC' THEN ec.endDate END DESC, " +
            "e.employeeId ASC")
    List<EmployeeDTO> findEmployees(@Param("employeeName") String employeeName,
                                    @Param("departmentId") Long departmentId,
                                    @Param("ordEmployeeName") String ordEmployeeName,
                                    @Param("ordCertificationName") String ordCertificationName,
                                    @Param("ordEndDate") String ordEndDate,
                                    Pageable pageable);


    /**
     * Đếm số lượng nhân viên thỏa mãn tiêu chí tìm kiếm.
     *
     * @param employeeName Tên nhân viên cần tìm (hoặc một phần)
     * @param departmentId ID phòng ban của nhân viên
     * @return Số lượng nhân viên thỏa mãn điều kiện
     */
    @Query("SELECT COUNT(e.employeeId) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.certifications ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "AND (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%'))")
    Long countEmployees(@Param("employeeName") String employeeName,
                        @Param("departmentId") Long departmentId);

    /**
     * Kiểm tra sự tồn tại của nhân viên theo employeeLoginId.
     *
     * @param employeeLoginId ID đăng nhập của nhân viên
     * @return true nếu tồn tại, ngược lại false
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);
}
