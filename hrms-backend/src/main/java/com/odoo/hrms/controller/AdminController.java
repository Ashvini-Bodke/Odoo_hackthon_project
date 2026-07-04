package com.odoo.hrms.controller;


import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.DashboardSummaryDTO;
import com.odoo.hrms.dto.EmployeeListDTO;
import com.odoo.hrms.dto.LeaveResponseDTO;
import com.odoo.hrms.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('HR')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        return ResponseEntity.ok(adminService.getDashboardSummary());
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeListDTO>> getAllEmployees() {
        return ResponseEntity.ok(adminService.getAllEmployees());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeListDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getEmployeeById(id));
    }

    @GetMapping("/attendance/today")
    public ResponseEntity<List<AttendenceResponceDTO>> getTodayAttendance() {
        return ResponseEntity.ok(adminService.getTodayAttendance());
    }

    @GetMapping("/leaves/pending")
    public ResponseEntity<List<LeaveResponseDTO>> getPendingLeaves() {
        return ResponseEntity.ok(adminService.getPendingLeaves());
    }
}