package com.odoo.hrms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.odoo.hrms.dto.AttendenceResponceDTO;
import com.odoo.hrms.dto.DashboardSummaryDTO;
import com.odoo.hrms.dto.EmployeeListDTO;
import com.odoo.hrms.dto.LeaveResponseDTO;
import com.odoo.hrms.entity.Attendence;
import com.odoo.hrms.entity.LeaveRequest;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.enums.AttendenceStatus;
import com.odoo.hrms.enums.LeaveStatus;
import com.odoo.hrms.exception.ResourceNotFoundException;
import com.odoo.hrms.repository.AttendenceRepository;
import com.odoo.hrms.repository.LeaveRequestRepository;
import com.odoo.hrms.repository.UserRepository;

@Service

public class AdminServiceImpl  implements AdminService{
    

     private final UserRepository userRepository;
    private final AttendenceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            AttendenceRepository attendanceRepository,
                            LeaveRequestRepository leaveRequestRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }


     @Override
    public DashboardSummaryDTO getDashboardSummary() {
        LocalDate today = LocalDate.now();

        // Total employees (only EMPLOYEE role, not HR)
        List<User> employees = userRepository.findByRole(com.odoo.hrms.enums.Role.EMPLOYEE);
        long totalEmployees = employees.size();

        // Today's attendance records
        List<Attendence> todayAttendance = attendanceRepository.findByDate(today);


         // Count statuses
        long present = todayAttendance.stream()
                .filter(a -> a.getAttendenceStatus() == AttendenceStatus.PRESENT)
                .count();
        long halfDay = todayAttendance.stream()
                .filter(a -> a.getAttendenceStatus() == AttendenceStatus.HALF_DAY)
                .count();
        long inOffice = todayAttendance.stream()
                .filter(a -> a.getAttendenceStatus() == AttendenceStatus.IN_OFFICE)
                .count();

        // Employees on leave today (approved leave covering today)
        long onLeave = leaveRequestRepository.countApprovedLeaveForDate(today);


         // Employees who have NOT marked attendance and are NOT on leave
        long marked = present + halfDay + inOffice + onLeave;
        long notMarked = totalEmployees - marked;

        return DashboardSummaryDTO.builder()
                .totalEmployees(totalEmployees)
                .todayPresent(present + inOffice) // Treat IN_OFFICE as present
                .todayAbsent(notMarked) // Those who neither checked in nor on leave
                .onLeave(onLeave)
                .halfDay(halfDay)
                .notMarked(notMarked)
                .build();
    }

      @Override
    public List<EmployeeListDTO> getAllEmployees() {
        List<User> employees = userRepository.findByRole(com.odoo.hrms.enums.Role.EMPLOYEE);
        return employees.stream()
                .map(this::buildEmployeeListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeListDTO getEmployeeById(Long id) {
        User employee = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return buildEmployeeListDTO(employee);
    }

    @Override
    public List<AttendenceResponceDTO> getTodayAttendance() {
        LocalDate today = LocalDate.now();
        List<Attendence> list = attendanceRepository.findByDate(today);
        return list.stream()
                .map(this::mapAttendanceToDTO)
                .collect(Collectors.toList());
    }

      @Override
    public List<LeaveResponseDTO> getPendingLeaves() {
        List<LeaveRequest> pendingLeaves = leaveRequestRepository.findByStatus(LeaveStatus.PENDING);
        return pendingLeaves.stream()
                .map(this::mapLeaveToDTO)
                .collect(Collectors.toList());
    }

    // ========== Helper Mappers ==========

    private EmployeeListDTO buildEmployeeListDTO(User user) {
        // Get today's status (using same logic as dashboard card)
        LocalDate today = LocalDate.now();
        Attendence todayAttendance = attendanceRepository.findByEmployeeAndDate(user, today).orElse(null);
        String status;
        if (todayAttendance != null && todayAttendance.getCheckIn() != null
                && todayAttendance.getCheckOut() == null) {
            status = "IN_OFFICE";
        } else {
            boolean onLeave = leaveRequestRepository.existsByEmployeeAndStatusAndDateRange(
                    user, LeaveStatus.APPROVED, today);
            status = onLeave ? "ON_LEAVE" : "ABSENT";
        }
        return EmployeeListDTO.builder()
                .id(user.getId())
                .employeeId(user.getEmployeeId())
                .email(user.getEmail())
                .name(user.getEmail()) // Replace with proper name fields if available
                .phone(null) // Placeholder
                .profilePicture(null)
                .status(status)
                .build();

                }

    private AttendenceResponceDTO mapAttendanceToDTO(Attendence attendance) {
        return AttendenceResponceDTO.builder()
                .id(attendance.getId())
                .employeeEmail(attendance.getEmployee().getEmail())
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getAttendenceStatus())
                .build();
    }

      private LeaveResponseDTO mapLeaveToDTO(LeaveRequest leave) {
        return LeaveResponseDTO.builder()
                .id(leave.getId())
                .employeeName(leave.getEmployee().getEmail())
                .employeeEmail(leave.getEmployee().getEmail())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .remarks(leave.getRemarks())
                .status(leave.getStatus())
                .appliedDate(leave.getAppliedDate())
                .approvedByName(leave.getApprovedBy() != null ? leave.getApprovedBy().getEmail() : null)
                .approvedDate(leave.getApprovedDate())
                .approvalRemarks(leave.getApprovalRemarks())
                .build();
    }

}
