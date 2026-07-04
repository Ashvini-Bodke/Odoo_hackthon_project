// package com.odoo.hrms.service;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.odoo.hrms.dto.AttendenceResponceDTO;
// import com.odoo.hrms.dto.DailyAttendanceDTO;
// import com.odoo.hrms.dto.EmployeeCardDTO;
// import com.odoo.hrms.dto.WeeklyAttendanceDTO;
// import com.odoo.hrms.entity.Attendence;
// import com.odoo.hrms.entity.User;
// import com.odoo.hrms.enums.AttendenceStatus;
// import com.odoo.hrms.enums.LeaveStatus;
// import com.odoo.hrms.exception.ResourceNotFoundException;
// import com.odoo.hrms.repository.AttendenceRepository;
// import com.odoo.hrms.repository.LeaveRequestRepository;
// import com.odoo.hrms.repository.UserRepository;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class AttendanceServiceImpl implements AttendanceService {

//     private final AttendenceRepository attendanceRepository;
    

//     public AttendanceServiceImpl(AttendenceRepository attendanceRepository) {
//         this.attendanceRepository = attendanceRepository;
//     }

//     @Override
//     @Transactional
//     public AttendenceResponceDTO checkIn(User employee) {
//         LocalDate today = LocalDate.now();

//         // Check if already checked in today
//         attendanceRepository.findByEmployeeAndDate(employee, today)
//                 .ifPresent(a -> {
//                     throw new IllegalStateException("Already checked in today");
//                 });

//         Attendence attendance = new Attendence();
//         attendance.setEmployee(employee);
//         attendance.setDate(today);
//         attendance.setCheckIn(LocalDateTime.now());
//         attendance.setAttendenceStatus(AttendenceStatus.IN_OFFICE);

//         Attendence saved = attendanceRepository.save(attendance);
//         return mapToDTO(saved);
//     }

//     @Override
//     @Transactional
//     public AttendenceResponceDTO checkOut(User employee) {
//         LocalDate today = LocalDate.now();

//         Attendence attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
//                 .orElseThrow(() -> new ResourceNotFoundException("No check-in found for today"));

//         if (attendance.getCheckOut() != null) {
//             throw new IllegalStateException("Already checked out today");
//         }

//         attendance.setCheckOut(LocalDateTime.now());
//         // Optionally update status to PRESENT if check-out time > 4 hours etc.
//         attendance.setAttendenceStatus(AttendenceStatus.PRESENT);

//         Attendence updated = attendanceRepository.save(attendance);
//         return mapToDTO(updated);
//     }

//     @Override
//     public AttendenceResponceDTO getTodayAttendance(User employee) {
//         LocalDate today = LocalDate.now();
//         return attendanceRepository.findByEmployeeAndDate(employee, today)
//                 .map(this::mapToDTO)
//                 .orElseThrow(() -> new ResourceNotFoundException("No attendance record for today"));
//     }

//     @Override
//     public WeeklyAttendanceDTO getWeeklyAttendance(User employee, LocalDate startDate) {
//         LocalDate endDate = startDate.plusDays(6);
//         List<Attendence> attendanceList = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);



//                 List<DailyAttendanceDTO> days = attendanceList.stream()
//                 .map(a->DailyAttendanceDTO.builder().date(a.getDate())
//             .attendenceStatus(a.getAttendenceStatus()).build()).collect(Collectors.toList());
                

//         return WeeklyAttendanceDTO.builder()
//                 .employeeEmail(employee.getEmail())
//                 .days(days)
//                 .build();
//     }




//     private final LeaveRequestRepository leaveRequestRepository;
//     private final UserRepository userRepository;
    
//     private AttendenceResponceDTO mapToDTO(Attendence attendance) {
//         return AttendenceResponceDTO.builder()
//                 .id(attendance.getId())
//                 .employeeEmail(attendance.getEmployee().getEmail())
//                 .date(attendance.getDate())
//                 .checkIn(attendance.getCheckIn())
//                 .checkOut(attendance.getCheckOut())
//                 .status(attendance.getAttendenceStatus())
//                 .build();



//     }


// //--------------
//     @Override
//     public EmployeeCardDTO getMyCard(User employee) {
//         return buildCard(employee);
//     }

//      @Override
//     public List<EmployeeCardDTO> getAllEmployeeCards() {
//         List<User> employees = userRepository.findByRole(com.odoo.hrms.enums.Role.EMPLOYEE);
//         return employees.stream()
//                 .map(this::buildCard)
//                 .collect(Collectors.toList());
//     }

//      private EmployeeCardDTO buildCard(User employee) {
//         LocalDate today = LocalDate.now();

//         // 1. Check today's attendance
//         Attendence todayAttendance = attendanceRepository
//                 .findByEmployeeAndDate(employee, today)
//                 .orElse(null);

//         String status;

//           if (todayAttendance != null && todayAttendance.getCheckIn() != null
//                 && todayAttendance.getCheckOut() == null) {
//             // Checked in but not out -> IN_OFFICE
//             status = "IN_OFFICE";
//         } else {
//             // 2. Check if employee has an approved leave for today
//             boolean onLeave = leaveRequestRepository
//                     .existsByEmployeeAndStatusAndDateRange(employee, LeaveStatus.APPROVED, today);
//             if (onLeave) {
//                 status = "ON_LEAVE";
//             } else {
//                 status = "ABSENT";
//             }
//         }

//         return EmployeeCardDTO.builder()

//         .id(employee.getId())
//                 .name(employee.getEmail()) // Change to firstName+lastName if available
//                 .loginId(employee.getEmployeeId())
//                 .email(employee.getEmail())
//                 .profilePicture(null) // Placeholder
//                 .status(status)
//                 .build();
//     }
//      private AttendenceResponceDTO mapToDTO(Attendence attendance) {
//         return AttendenceResponceDTO.builder()
//                 .id(attendance.getId())
//                 .employeeEmail(attendance.getEmployee().getEmail())
//                 .date(attendance.getDate())
//                 .checkIn(attendance.getCheckIn())
//                 .checkOut(attendance.getCheckOut())
//                 .status(attendance.getAttendenceStatus())
//                 .build();
//     }
// }


package com.odoo.hrms.service;

import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.DailyAttendanceDTO;
import com.odoo.hrms.dto.EmployeeCardDTO;
import com.odoo.hrms.dto.WeeklyAttendanceDTO;
import com.odoo.hrms.entity.Attendence;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.enums.AttendenceStatus;
import com.odoo.hrms.enums.LeaveStatus;
import com.odoo.hrms.exception.ResourceNotFoundException;
import com.odoo.hrms.repository.AttendenceRepository;
import com.odoo.hrms.repository.LeaveRequestRepository;
import com.odoo.hrms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendenceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    // Constructor with all three repositories
    public AttendanceServiceImpl(AttendenceRepository attendanceRepository,
                                 LeaveRequestRepository leaveRequestRepository,
                                 UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
    }

    // ================== Check-in/Check-out Methods ==================

    @Override
    @Transactional
    public AttendenceResponceDTO checkIn(User employee) {
        LocalDate today = LocalDate.now();

        attendanceRepository.findByEmployeeAndDate(employee, today)
                .ifPresent(a -> {
                    throw new IllegalStateException("Already checked in today");
                });

        Attendence attendance = new Attendence();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setAttendenceStatus(AttendenceStatus.IN_OFFICE);

        Attendence saved = attendanceRepository.save(attendance);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public AttendenceResponceDTO checkOut(User employee) {
        LocalDate today = LocalDate.now();

        Attendence attendance = attendanceRepository.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new ResourceNotFoundException("No check-in found for today"));

        if (attendance.getCheckOut() != null) {
            throw new IllegalStateException("Already checked out today");
        }

        attendance.setCheckOut(LocalDateTime.now());
        attendance.setAttendenceStatus(AttendenceStatus.PRESENT);

        Attendence updated = attendanceRepository.save(attendance);
        return mapToDTO(updated);
    }

    @Override
    public AttendenceResponceDTO getTodayAttendance(User employee) {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByEmployeeAndDate(employee, today)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No attendance record for today"));
    }

    @Override
    public WeeklyAttendanceDTO getWeeklyAttendance(User employee, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        List<Attendence> attendanceList = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);

        List<DailyAttendanceDTO> days = attendanceList.stream()
                .map(a -> DailyAttendanceDTO.builder()
                        .date(a.getDate())
                        .attendenceStatus(a.getAttendenceStatus())
                        .build())
                .collect(Collectors.toList());

        return WeeklyAttendanceDTO.builder()
                .employeeEmail(employee.getEmail())
                .days(days)
                .build();
    }

    // ================== Dashboard Card Methods (NEW) ==================

    @Override
    public EmployeeCardDTO getMyCard(User employee) {
        return buildCard(employee);
    }

    @Override
    public List<EmployeeCardDTO> getAllEmployeeCards() {
        List<User> employees = userRepository.findByRole(com.odoo.hrms.enums.Role.EMPLOYEE);
        return employees.stream()
                .map(this::buildCard)
                .collect(Collectors.toList());
    }

    // ================== Helper Methods ==================

    private EmployeeCardDTO buildCard(User employee) {
        LocalDate today = LocalDate.now();

        // 1. Check today's attendance
        Attendence todayAttendance = attendanceRepository
                .findByEmployeeAndDate(employee, today)
                .orElse(null);

        String status;

        if (todayAttendance != null && todayAttendance.getCheckIn() != null
                && todayAttendance.getCheckOut() == null) {
            // Checked in but not out -> IN_OFFICE
            status = "IN_OFFICE";
        } else {
            // 2. Check if employee has an approved leave for today
            boolean onLeave = leaveRequestRepository
                    .existsByEmployeeAndStatusAndDateRange(employee, LeaveStatus.APPROVED, today);
            if (onLeave) {
                status = "ON_LEAVE";
            } else {
                status = "ABSENT";
            }
        }

        return EmployeeCardDTO.builder()
                .id(employee.getId())
                .name(employee.getEmail())
                .loginId(employee.getEmployeeId())
                .email(employee.getEmail())
                .profilePicture(null)
                .status(status)
                .build();
    }

    private AttendenceResponceDTO mapToDTO(Attendence attendance) {
        return AttendenceResponceDTO.builder()
                .id(attendance.getId())
                .employeeEmail(attendance.getEmployee().getEmail())
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getAttendenceStatus())
                .build();
    }
}