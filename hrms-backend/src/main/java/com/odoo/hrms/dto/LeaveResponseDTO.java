package com.odoo.hrms.dto;

import com.odoo.hrms.enums.LeaveStatus;
import com.odoo.hrms.enums.LeaveType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LeaveResponseDTO {
    private Long id;
    private String employeeName;
    private String employeeEmail;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;
    private LeaveStatus status;
    private LocalDateTime appliedDate;
    private String approvedByName;
    private LocalDateTime approvedDate;
    private String approvalRemarks;
}