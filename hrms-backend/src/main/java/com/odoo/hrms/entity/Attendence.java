package com.odoo.hrms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.odoo.hrms.enums.AttendenceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;




@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Attendence {
    

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(nullable = false)
    private LocalDate date;

     @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendenceStatus attendenceStatus; // PRESENT, ABSENT, HALF_DAY, LEAVE, IN_OFFICE

    @CreationTimestamp
    private LocalDateTime createdAt;
}
