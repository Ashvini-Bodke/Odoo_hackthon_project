package com.hrms.controller;

import com.hrms.dto.EmployeeResponseDTO;
import com.hrms.entity.User;
import com.hrms.repository.UserRepository;
import com.hrms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<User> employees = userRepository.findAll().stream()
                .filter(u -> u.getRole().toString().equals("EMPLOYEE"))
                .toList();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("total", employees.size());
        response.put("data", employees);
        
        return ResponseEntity.ok(response);
    }
}
