package com.hrms.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "employee_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String firstName;
    private String lastName;
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
    private String expCode;
    private String skills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
