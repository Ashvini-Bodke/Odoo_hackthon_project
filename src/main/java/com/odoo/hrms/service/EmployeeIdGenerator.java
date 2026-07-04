package com.odoo.hrms.service;

import org.springframework.stereotype.Service;

@Service
public class EmployeeIdGenerator {

    private static int serialNumber = 1;

    public String generateEmployeeId(String firstName, String lastName) {

        String companyCode = "OI";

        String first = firstName.length() >= 2
                ? firstName.substring(0, 2).toUpperCase()
                : firstName.toUpperCase();

        String last = lastName.length() >= 2
                ? lastName.substring(0, 2).toUpperCase()
                : lastName.toUpperCase();

        int year = java.time.Year.now().getValue();

        String serial = String.format("%04d", serialNumber++);

        return companyCode + first + last + year + serial;
    }
}