package com.hrms.service;
import com.hrms.dto.EmployeeCardDTO;
import com.hrms.entity.Attendance;
import com.hrms.entity.User;
import java.time.LocalDate;
import java.util.List;
public interface AttendanceService {
    Attendance checkIn(User user);
    Attendance checkOut(User user);
    Attendance getTodayAttendance(User user);
    List<Attendance> getWeeklyAttendance(User user, LocalDate startDate);
    List<EmployeeCardDTO> getDashboardCards();
    List<Attendance> getAttendanceByDate(LocalDate date);
}
