package com.hrms.service;

import com.hrms.dto.EmployeeRequestDTO;
import com.hrms.dto.EmployeeResponseDTO;
import com.hrms.entity.User;

import java.util.List;

public interface EmployeeService {
    
    /**
     * Create new employee with auto-generated ID and password
     */
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO request);
    
    /**
     * Get all employees (Admin only)
     */
    List<EmployeeResponseDTO> getAllEmployees();
    
    /**
     * Get employee by ID (Admin only)
     */
    EmployeeResponseDTO getEmployeeById(Long id);
    
    /**
     * Update employee (Admin only)
     */
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request);
    
    /**
     * Delete employee (Admin only)
     */
    void deleteEmployee(Long id);
}