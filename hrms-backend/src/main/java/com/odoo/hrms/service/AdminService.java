package com.odoo.hrms.service;

import java.util.List;

import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.DashboardSummaryDTO;
import com.odoo.hrms.dto.EmployeeListDTO;
import com.odoo.hrms.dto.LeaveResponseDTO;

public interface AdminService {
    
  public  DashboardSummaryDTO getDashboardSummary();

    List<EmployeeListDTO> getAllEmployees();

    EmployeeListDTO getEmployeeById(Long id);

    List<AttendenceResponceDTO> getTodayAttendance();

    List<LeaveResponseDTO> getPendingLeaves();
}
