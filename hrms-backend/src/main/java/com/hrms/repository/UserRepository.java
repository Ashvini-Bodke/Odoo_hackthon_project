package com.hrms.repository;
import com.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'EMPLOYEE'")
    long countEmployees();
}
