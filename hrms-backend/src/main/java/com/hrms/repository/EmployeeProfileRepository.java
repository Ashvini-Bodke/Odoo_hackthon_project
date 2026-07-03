package com.hrms.repository;
import com.hrms.entity.EmployeeProfile;
import com.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByUser(User user);
    Optional<EmployeeProfile> findByUserId(Long userId);
}
