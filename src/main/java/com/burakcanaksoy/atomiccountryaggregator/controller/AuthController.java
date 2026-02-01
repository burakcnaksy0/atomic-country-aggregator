package com.burakcanaksoy.atomiccountryaggregator.controller;

import com.burakcanaksoy.atomiccountryaggregator.dto.ApiResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.AuthResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.LoginRequest;
import com.burakcanaksoy.atomiccountryaggregator.dto.RegisterRequest;
import com.burakcanaksoy.atomiccountryaggregator.service.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(ApiResponse.success("Record created successfully",authService.register(registerRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(ApiResponse.success("Login",authService.login(loginRequest)));
    }
}
