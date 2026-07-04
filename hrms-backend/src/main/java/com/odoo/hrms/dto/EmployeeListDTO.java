package com.odoo.hrms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeListDTO {
    
    private Long id;
    private String employeeId;
    private String email;
    private String name;
    private String phone;
    private String profilePicture;
    private String status; // Today's status
}
