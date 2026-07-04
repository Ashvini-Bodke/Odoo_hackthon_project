package com.odoo.hrms.repository;

import com.odoo.hrms.entity.LeaveRequest;
import com.odoo.hrms.enums.LeaveStatus;
import com.odoo.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    
    List<LeaveRequest> findByEmployee(User employee);

    List<LeaveRequest> findByEmployeeAndStatus(User employee, LeaveStatus status);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    Optional<LeaveRequest> findByIdAndEmployee(Long id, User employee);
     

      @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l " +
           "WHERE l.employee = :employee " +
           "AND l.status != 'REJECTED' " +
           "AND (:startDate BETWEEN l.startDate AND l.endDate " +
           "     OR :endDate BETWEEN l.startDate AND l.endDate " +
           "     OR (l.startDate BETWEEN :startDate AND :endDate))")
    boolean existsOverlappingLeave(@Param("employee") User employee,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);




                                   @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l " +
       "WHERE l.employee = :employee " +
       "AND l.status = :status " +
       "AND :date BETWEEN l.startDate AND l.endDate")
boolean existsByEmployeeAndStatusAndDateRange(@Param("employee") User employee,
                                              @Param("status") LeaveStatus status,
                                              @Param("date") LocalDate date);




                                              @Query("SELECT COUNT(l) FROM LeaveRequest l " +
       "WHERE l.status = :status " +
       "AND :date BETWEEN l.startDate AND l.endDate")
long countApprovedLeaveForDate(@Param("date") LocalDate date);

}
