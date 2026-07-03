package com.hrms.service.impl;
import com.hrms.dto.EmployeeCardDTO;
import com.hrms.entity.Attendance;
import com.hrms.entity.AttendanceStatus;
import com.hrms.entity.LeaveRequest;
import com.hrms.entity.LeaveStatus;
import com.hrms.entity.User;
import com.hrms.repository.AttendanceRepository;
import com.hrms.repository.LeaveRequestRepository;
import com.hrms.repository.UserRepository;
import com.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    @Override
    @Transactional
    public Attendance checkIn(User user) {
        LocalDate today = LocalDate.now();
        Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndDate(user, today);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            if (attendance.getCheckIn() != null) {
                throw new RuntimeException("Already checked in today at: " + attendance.getCheckIn());
            }
            attendance.setCheckIn(LocalDateTime.now());
            attendance.setStatus(AttendanceStatus.PRESENT);
            return attendanceRepository.save(attendance);
        }
        Attendance attendance = Attendance.builder()
            .user(user)
            .date(today)
            .checkIn(LocalDateTime.now())
            .status(AttendanceStatus.PRESENT)
            .build();
        return attendanceRepository.save(attendance);
    }
    @Override
    @Transactional
    public Attendance checkOut(User user) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
            .orElseThrow(() -> new RuntimeException("No check-in found for today"));
        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out today at: " + attendance.getCheckOut());
        }
        LocalDateTime checkOutTime = LocalDateTime.now();
        attendance.setCheckOut(checkOutTime);
        if (attendance.getCheckIn() != null) {
            double hours = ChronoUnit.MINUTES.between(attendance.getCheckIn(), checkOutTime) / 60.0;
            attendance.setTotalHours(Math.round(hours * 100.0) / 100.0);
        }
        return attendanceRepository.save(attendance);
    }
    @Override
    public Attendance getTodayAttendance(User user) {
        return attendanceRepository.findByUserAndDate(user, LocalDate.now()).orElse(null);
    }
    @Override
    public List<Attendance> getWeeklyAttendance(User user, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        return attendanceRepository.findByUserAndDateBetween(user, startDate, endDate);
    }
    @Override
    public List<EmployeeCardDTO> getDashboardCards() {
        List<User> employees = userRepository.findAll().stream()
            .filter(u -> u.getRole().toString().equals("EMPLOYEE"))
            .toList();
        List<EmployeeCardDTO> dashboardCards = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (User employee : employees) {
            String status = determineStatus(employee, today);
            EmployeeCardDTO card = EmployeeCardDTO.builder()
                .id(employee.getId())
                .name(employee.getProfile() != null ? 
                    employee.getProfile().getFirstName() + " " + employee.getProfile().getLastName() : 
                    employee.getEmail())
                .employeeId(employee.getEmployeeId())
                .email(employee.getEmail())
                .profilePicture(employee.getProfile() != null ? 
                    employee.getProfile().getProfilePicture() : null)
                .status(status)
                .designation(employee.getProfile() != null ? 
                    employee.getProfile().getSkills() : null)
                .department("Engineering")
                .build();
            dashboardCards.add(card);
        }
        return dashboardCards;
    }
    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
    private String determineStatus(User user, LocalDate date) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByUserAndStatus(user, LeaveStatus.APPROVED);
        boolean onLeave = leaves.stream().anyMatch(leave -> 
            !leave.getStartDate().isAfter(date) && !leave.getEndDate().isBefore(date)
        );
        if (onLeave) {
            return "ON_LEAVE";
        }
        Optional<Attendance> todayAttendance = attendanceRepository.findByUserAndDate(user, date);
        if (todayAttendance.isPresent() && todayAttendance.get().getCheckIn() != null) {
            return "IN_OFFICE";
        }
        return "ABSENT";
    }
}
