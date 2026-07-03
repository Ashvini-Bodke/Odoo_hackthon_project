package com.odoo.hrms.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odoo.hrms.dto.AuthRequest;
import com.odoo.hrms.dto.AuthResponse;
import com.odoo.hrms.dto.SignUpRequest;
import com.odoo.hrms.entity.User;
import com.odoo.hrms.repository.UserRepository;
import com.odoo.hrms.security.JwtUtil;

@Service
public class AuthServiceImpl  implements AuthService{
    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

     @Override
    public String register(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmployeeId(request.getEmployeeId());
        user.setRole(request.getRole() != null ? request.getRole() : Role.EMPLOYEE);

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}
