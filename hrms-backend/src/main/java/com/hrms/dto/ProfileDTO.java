package com.hrms.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String employeeId;
    private String phone;
    private String address;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private String nationality;
    private String personalEmail;
    private String gender;
    private String maritalStatus;
    private LocalDate dateOfJoining;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String panNumber;
    private String uanNumber;
    private String skills;
}
