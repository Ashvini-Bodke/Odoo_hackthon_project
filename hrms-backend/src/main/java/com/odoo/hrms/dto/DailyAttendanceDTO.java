package com.odoo.hrms.dto;

import java.time.LocalDate;

import com.odoo.hrms.enums.AttendenceStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyAttendanceDTO {
     private LocalDate date;
    private AttendenceStatus attendenceStatus;
}
