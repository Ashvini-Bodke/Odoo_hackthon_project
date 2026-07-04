package com.odoo.hrms.dto;

import com.odoo.hrms.enums.LeaveType;

import lombok.Data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;



@Data
public class LeaveApplyRequest {
    
   
    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in future")
    private LocalDate endDate;

    private String remarks;

}
