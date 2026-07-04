package com.odoo.hrms.service;

import com.odoo.hrms.dto.LeaveApplyRequest;
import com.odoo.hrms.dto.LeaveApprovalRequest;
import com.odoo.hrms.dto.LeaveResponseDTO;
import com.odoo.hrms.entity.LeaveRequest;
import com.odoo.hrms.enums.LeaveStatus;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.exception.ResourceNotFoundException;
import com.odoo.hrms.exception.LeaveConflictException;
import com.odoo.hrms.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl  implements LeaveService{
    

     private final LeaveRequestRepository leaveRequestRepository;

    public LeaveServiceImpl(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

     @Override
    @Transactional
    public LeaveResponseDTO applyLeave(User employee, LeaveApplyRequest request) {
        // Validate dates
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // Check overlapping leaves
        if (leaveRequestRepository.existsOverlappingLeave(employee, request.getStartDate(), request.getEndDate())) {
            throw new LeaveConflictException("You already have a leave request for these dates");
        }

         LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setRemarks(request.getRemarks());
        leave.setStatus(LeaveStatus.PENDING);

        LeaveRequest saved = leaveRequestRepository.save(leave);
        return mapToDTO(saved);
    }

      @Override
    public List<LeaveResponseDTO> getMyLeaves(User employee) {
        return leaveRequestRepository.findByEmployee(employee)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

     @Override
    public List<LeaveResponseDTO> getMyPendingLeaves(User employee) {
        return leaveRequestRepository.findByEmployeeAndStatus(employee, LeaveStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDTO getLeaveById(Long id, User employee) {
        LeaveRequest leave = leaveRequestRepository.findByIdAndEmployee(id, employee)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found or access denied"));
        return mapToDTO(leave);
    }


     @Override
    public List<LeaveResponseDTO> getAllPendingLeaves() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

      @Override
    @Transactional
    public LeaveResponseDTO approveLeave(LeaveApprovalRequest request, User approver) {
        LeaveRequest leave = leaveRequestRepository.findById(request.getLeaveId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Leave request is already " + leave.getStatus());
        }

          leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprovedBy(approver);
        leave.setApprovedDate(LocalDateTime.now());
        leave.setApprovalRemarks(request.getRemarks());

        LeaveRequest updated = leaveRequestRepository.save(leave);
        return mapToDTO(updated);
    }

     @Override
    @Transactional
    public LeaveResponseDTO rejectLeave(LeaveApprovalRequest request, User approver) {
        LeaveRequest leave = leaveRequestRepository.findById(request.getLeaveId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Leave request is already " + leave.getStatus());
        }

         leave.setStatus(LeaveStatus.REJECTED);
        leave.setApprovedBy(approver);
        leave.setApprovedDate(LocalDateTime.now());
        leave.setApprovalRemarks(request.getRemarks());

        LeaveRequest updated = leaveRequestRepository.save(leave);
        return mapToDTO(updated);
    }

     private LeaveResponseDTO mapToDTO(LeaveRequest leave) {
        return LeaveResponseDTO.builder()
                .id(leave.getId())
                .employeeName(leave.getEmployee().getEmail()) // You can replace with proper name if available
                .employeeEmail(leave.getEmployee().getEmail())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .remarks(leave.getRemarks())
                .status(leave.getStatus())
                .appliedDate(leave.getAppliedDate())
                .approvedByName(leave.getApprovedBy() != null ? leave.getApprovedBy().getEmail() : null)
                 .approvedDate(leave.getApprovedDate())
                .approvalRemarks(leave.getApprovalRemarks())
                .build();
     }
}
