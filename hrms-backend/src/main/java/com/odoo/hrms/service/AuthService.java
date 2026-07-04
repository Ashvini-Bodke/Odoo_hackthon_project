package com.odoo.hrms.service;

import com.odoo.hrms.dto.AuthRequest;
import com.odoo.hrms.dto.AuthResponse;
import com.odoo.hrms.dto.SignUpRequest;

public interface AuthService {
     String register(SignUpRequest request);
    AuthResponse authenticate(AuthRequest request);
}
