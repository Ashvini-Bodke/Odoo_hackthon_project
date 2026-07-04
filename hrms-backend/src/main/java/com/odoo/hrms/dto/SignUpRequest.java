package com.odoo.hrms.dto;

import com.odoo.hrms.enums.Role;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String employeeId;
    private Role role; // Optional, default EMPLOYEE
}
