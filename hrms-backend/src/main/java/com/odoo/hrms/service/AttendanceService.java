package com.odoo.hrms.service;

import java.time.LocalDate;
import java.util.List;

import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.EmployeeCardDTO;
import com.odoo.hrms.dto.WeeklyAttendanceDTO;
import com.odoo.hrms.entity.User;

public interface AttendanceService {
 
    AttendenceResponceDTO checkIn(User employee);
    AttendenceResponceDTO checkOut(User employee);

     AttendenceResponceDTO getTodayAttendance(User employee);

    WeeklyAttendanceDTO getWeeklyAttendance(User employee, LocalDate startDate);


    EmployeeCardDTO getMyCard(User employee);

    List<EmployeeCardDTO> getAllEmployeeCards();
}
