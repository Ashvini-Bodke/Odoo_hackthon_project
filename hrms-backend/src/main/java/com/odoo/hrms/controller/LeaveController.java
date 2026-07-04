// package com.odoo.hrms.controller;

// import com.odoo.hrms.dto.LeaveApplyRequest;
// import com.odoo.hrms.dto.LeaveApprovalRequest;
// import com.odoo.hrms.dto.LeaveResponseDTO;
// import com.odoo.hrms.entity.User;
// import com.odoo.hrms.service.LeaveService;
// import jakarta.validation.Valid;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.web.bind.annotation.*;


// import java.util.List;

// @RestController
// @RequestMapping("/api/leaves")
// public class LeaveController {

//     private final LeaveService leaveService;

//     public LeaveController(LeaveService leaveService) {
//         this.leaveService = leaveService;
//     }

//     // Employee applies for leave
//     @PostMapping("/apply")
//     public ResponseEntity<LeaveResponseDTO> applyLeave(
//             @AuthenticationPrincipal User employee,
//             @Valid @RequestBody LeaveApplyRequest request) {
//         LeaveResponseDTO response = leaveService.applyLeave(employee, request);
//         return new ResponseEntity<>(response, HttpStatus.CREATED);
//     }


//     // Employee views their own leaves
//     @GetMapping("/my")
//     public ResponseEntity<List<LeaveResponseDTO>> getMyLeaves(
//             @AuthenticationPrincipal User employee) {
//         return ResponseEntity.ok(leaveService.getMyLeaves(employee));
//     }

//     // Employee views only pending leaves
//     @GetMapping("/my/pending")
//     public ResponseEntity<List<LeaveResponseDTO>> getMyPendingLeaves(
//             @AuthenticationPrincipal User employee) {
//         return ResponseEntity.ok(leaveService.getMyPendingLeaves(employee));
//     }

//     // Employee views a specific leave by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<LeaveResponseDTO> getLeaveById(
//             @AuthenticationPrincipal User employee,
//             @PathVariable Long id) {
//         return ResponseEntity.ok(leaveService.getLeaveById(id, employee));
//     }

//     // Admin/HR views all pending leaves
//     @GetMapping("/pending")
//     @PreAuthorize("hasRole('HR')")
//     public ResponseEntity<List<LeaveResponseDTO>> getAllPendingLeaves() {
//         return ResponseEntity.ok(leaveService.getAllPendingLeaves());
//     }

//     // Admin/HR approves leave
//     @PutMapping("/approve")
//     @PreAuthorize("hasRole('HR')")
//     public ResponseEntity<LeaveResponseDTO> approveLeave(
//             @AuthenticationPrincipal User approver,
//             @RequestBody LeaveApprovalRequest request) {
//         return ResponseEntity.ok(leaveService.approveLeave(request, approver));
//     }

//     // Admin/HR rejects leave
//     @PutMapping("/reject")
//     @PreAuthorize("hasRole('HR')")
//     public ResponseEntity<LeaveResponseDTO> rejectLeave(
//             @AuthenticationPrincipal User approver,
//             @RequestBody LeaveApprovalRequest request) {
//         return ResponseEntity.ok(leaveService.rejectLeave(request, approver));
//     }
// }


package com.odoo.hrms.controller;

import com.odoo.hrms.dto.LeaveApplyRequest;
import com.odoo.hrms.dto.LeaveApprovalRequest;
import com.odoo.hrms.dto.LeaveResponseDTO;
import com.odoo.hrms.enums.Role;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.repository.UserRepository;
import com.odoo.hrms.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final UserRepository userRepository;   // <-- Add this

    public LeaveController(LeaveService leaveService, UserRepository userRepository) {
        this.leaveService = leaveService;
        this.userRepository = userRepository;
    }

    // Helper to fetch User from email
    private User getLoggedInUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Employee applies for leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveResponseDTO> applyLeave(
            @AuthenticationPrincipal String email,   // <-- Changed to String
            @Valid @RequestBody LeaveApplyRequest request) {
        User employee = getLoggedInUser(email);
        // Optional: manual role check (if you want to be extra safe)
        if (employee.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Access denied: Only employees can apply");
        }
        LeaveResponseDTO response = leaveService.applyLeave(employee, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Employee views their own leaves
    @GetMapping("/my")
    public ResponseEntity<List<LeaveResponseDTO>> getMyLeaves(
            @AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(leaveService.getMyLeaves(employee));
    }

    @GetMapping("/my/pending")
    public ResponseEntity<List<LeaveResponseDTO>> getMyPendingLeaves(
            @AuthenticationPrincipal String email) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(leaveService.getMyPendingLeaves(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponseDTO> getLeaveById(
            @AuthenticationPrincipal String email,
            @PathVariable Long id) {
        User employee = getLoggedInUser(email);
        return ResponseEntity.ok(leaveService.getLeaveById(id, employee));
    }

    // Admin/HR views all pending leaves
    @GetMapping("/pending")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<LeaveResponseDTO>> getAllPendingLeaves() {
        return ResponseEntity.ok(leaveService.getAllPendingLeaves());
    }

    // Admin/HR approves leave
    @PutMapping("/approve")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveResponseDTO> approveLeave(
            @AuthenticationPrincipal String email,   // <-- Changed to String
            @RequestBody LeaveApprovalRequest request) {
        User approver = getLoggedInUser(email);
        return ResponseEntity.ok(leaveService.approveLeave(request, approver));
    }

    @PutMapping("/reject")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveResponseDTO> rejectLeave(
            @AuthenticationPrincipal String email,
            @RequestBody LeaveApprovalRequest request) {
        User approver = getLoggedInUser(email);
        return ResponseEntity.ok(leaveService.rejectLeave(request, approver));
    }
}