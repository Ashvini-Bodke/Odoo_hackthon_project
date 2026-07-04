package com.odoo.hrms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeeklyAttendanceDTO {
    private String employeeEmail;
    private List<DailyAttendanceDTO> days;
}

