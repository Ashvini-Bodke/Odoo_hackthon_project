package com.odoo.hrms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class EmployeeCardDTO {
    
     private Long id;
    private String name;
    private String loginId;
    private String email;
    private String profilePicture;
    private String status; // "IN_OFFICE", "ON_LEAVE", "ABSENT"
}
