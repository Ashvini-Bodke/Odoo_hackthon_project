package com.odoo.hrms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary_components")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryComponents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basic;

    private double hra;

    private double standardAllowance;

    private double performanceBonus;

    private double lta;

    private double pf;

    private double professionalTax;

    private double grossSalary;

    private double netSalary;
}