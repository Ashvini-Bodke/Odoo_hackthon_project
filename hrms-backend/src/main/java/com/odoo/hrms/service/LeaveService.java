package com.odoo.hrms.service;

import java.util.List;

import com.odoo.hrms.dto.LeaveApplyRequest;
import com.odoo.hrms.dto.LeaveApprovalRequest;
import com.odoo.hrms.dto.LeaveResponseDTO;
import com.odoo.hrms.entity.User;


public interface LeaveService {

    LeaveResponseDTO applyLeave(User employee, LeaveApplyRequest request);
    List<LeaveResponseDTO> getMyLeaves(User employee);
    List<LeaveResponseDTO> getMyPendingLeaves(User employee);
    LeaveResponseDTO getLeaveById(Long id, User employee);
    List<LeaveResponseDTO> getAllPendingLeaves();  // HR only
    LeaveResponseDTO approveLeave(LeaveApprovalRequest request, User approver);
    LeaveResponseDTO rejectLeave(LeaveApprovalRequest request, User approver);
    
}
