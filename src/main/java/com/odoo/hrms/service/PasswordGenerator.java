package com.odoo.hrms.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGenerator {

    private static final String PREFIX = "Levisia";
    private static final Random random = new Random();

    public String generatePassword() {
        int number = 100 + random.nextInt(900);
        return PREFIX + number;
    }
}