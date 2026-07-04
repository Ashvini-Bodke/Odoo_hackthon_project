package com.odoo.hrms.repository;

import com.odoo.hrms.entity.User;
import com.odoo.hrms.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends  JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);
    Optional<User> findByEmployeeId(String employeeId);

    List<User> findByRole(Role role);
}
