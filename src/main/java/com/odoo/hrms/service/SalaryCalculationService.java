package com.odoo.hrms.service;

import org.springframework.stereotype.Service;

@Service
public class SalaryCalculationService {

    public void calculateSalary(double wage) {

        double basic = wage * 0.50;
        double hra = basic * 0.50;
        double standardAllowance = 4167;
        double performanceBonus = wage * 0.0833;
        double lta = wage * 0.08333;
        double pf = basic * 0.12;
        double professionalTax = 200;

        double grossSalary = basic + hra + standardAllowance
                + performanceBonus + lta;

        double netSalary = grossSalary - pf - professionalTax;

        System.out.println("Basic Salary : " + basic);
        System.out.println("HRA : " + hra);
        System.out.println("Standard Allowance : " + standardAllowance);
        System.out.println("Performance Bonus : " + performanceBonus);
        System.out.println("LTA : " + lta);
        System.out.println("PF : " + pf);
        System.out.println("Professional Tax : " + professionalTax);
        System.out.println("Gross Salary : " + grossSalary);
        System.out.println("Net Salary : " + netSalary);
    }
}