package com.odoo.hrms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryDTO {
    
    private long totalEmployees;
    private long todayPresent;
    private long todayAbsent;
    private long onLeave;
    private long halfDay;
    private long notMarked; // Neither check-in nor leave
}
