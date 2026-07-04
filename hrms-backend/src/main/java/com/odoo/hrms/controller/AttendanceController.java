package com.odoo.hrms.controller;


import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.EmployeeCardDTO;
import com.odoo.hrms.dto.WeeklyAttendanceDTO;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.repository.UserRepository;
import com.odoo.hrms.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    public AttendanceController(AttendanceService attendanceService, UserRepository userRepository) {
        this.attendanceService = attendanceService;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/checkin")
    public ResponseEntity<AttendenceResponceDTO> checkIn(
            @AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return new ResponseEntity<>(attendanceService.checkIn(employee), HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<AttendenceResponceDTO> checkOut(
            @AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(attendanceService.checkOut(employee));
    }

    @GetMapping("/today")
    public ResponseEntity<AttendenceResponceDTO> getTodayAttendance(
            @AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(attendanceService.getTodayAttendance(employee));
    }

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyAttendanceDTO> getWeeklyAttendance(
            @AuthenticationPrincipal String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(attendanceService.getWeeklyAttendance(employee, startDate));
    }


    // ============ Dashboard Card APIs (NEW) ============

    @GetMapping("/my-card")
    public ResponseEntity<EmployeeCardDTO> getMyCard(@AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(attendanceService.getMyCard(employee));
    }

    @GetMapping("/admin/all-cards")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<EmployeeCardDTO>> getAllEmployeeCards() {
        return ResponseEntity.ok(attendanceService.getAllEmployeeCards());
    }
}