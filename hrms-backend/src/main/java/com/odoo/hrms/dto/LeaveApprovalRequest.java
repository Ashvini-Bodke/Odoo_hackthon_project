package com.odoo.hrms.dto;

import lombok.Data;

@Data
public class LeaveApprovalRequest {
    private Long leaveId;
    private String remarks;  
}
