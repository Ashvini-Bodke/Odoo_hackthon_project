package com.hrms.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCardDTO {
    private Long id;
    private String name;
    private String employeeId;
    private String email;
    private String profilePicture;
    private String status; // "IN_OFFICE", "ON_LEAVE", "ABSENT"
    private String designation;
    private String department;
}
