package com.hrms.repository;
import com.hrms.entity.LeaveRequest;
import com.hrms.entity.LeaveStatus;
import com.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByUserAndStatus(User user, LeaveStatus status);
    List<LeaveRequest> findByStatus(LeaveStatus status);
    @Query("SELECT l FROM LeaveRequest l WHERE l.user = :user AND l.status = 'APPROVED' AND l.startDate <= :date AND l.endDate >= :date")
    List<LeaveRequest> findActiveLeavesForDate(@Param("user") User user, @Param("date") LocalDate date);
    @Query("SELECT l FROM LeaveRequest l WHERE l.user = :user AND l.status = 'APPROVED' AND ((l.startDate <= :endDate AND l.endDate >= :startDate))")
    List<LeaveRequest> findOverlappingLeaves(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
