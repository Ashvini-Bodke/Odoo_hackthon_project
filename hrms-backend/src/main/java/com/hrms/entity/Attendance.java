package com.hrms.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "attendance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private Double totalHours;
    private String remarks;
}
enum AttendanceStatus { PRESENT, ABSENT, HALF_DAY, ON_LEAVE }
