package com.odoo.hrms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.odoo.hrms.enums.AttendenceStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendenceResponceDTO {
    
    private Long id;
    private String employeeEmail;
    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private AttendenceStatus status;
}
