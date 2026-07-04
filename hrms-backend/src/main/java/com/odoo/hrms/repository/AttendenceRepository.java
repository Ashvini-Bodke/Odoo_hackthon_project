package com.odoo.hrms.repository;

import java.time.LocalDate;

import com.odoo.hrms.entity.Attendence;
import com.odoo.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendenceRepository  extends JpaRepository<Attendence,Long> {
    
    Optional<Attendence> findByEmployeeAndDate(User employee, LocalDate date);

    List<Attendence> findByEmployeeAndDateBetween(User employee, LocalDate start, LocalDate end);

    List<Attendence> findByDate(LocalDate date); // For admin dashboard

    
}
