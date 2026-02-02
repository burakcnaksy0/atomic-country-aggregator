package com.burakcanaksoy.atomiccountryaggregator.controller;

import com.burakcanaksoy.atomiccountryaggregator.request.LoginRequest;
import com.burakcanaksoy.atomiccountryaggregator.request.RegisterRequest;
import com.burakcanaksoy.atomiccountryaggregator.response.ApiResponse;
import com.burakcanaksoy.atomiccountryaggregator.response.AuthResponse;
import com.burakcanaksoy.atomiccountryaggregator.response.UserResponse;
import com.burakcanaksoy.atomiccountryaggregator.service.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return ResponseEntity.ok(ApiResponse.success("Record created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(ApiResponse.success("Login",authService.login(loginRequest)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody RegisterRequest registerRequest){
        authService.registerWithSql(registerRequest);
        return ResponseEntity.ok(ApiResponse.success("Record created"));
    }

    @GetMapping("/user/count")
    public ResponseEntity<ApiResponse<Integer>> countUser(){
        int countUser = authService.countUser();
        return ResponseEntity.ok(ApiResponse.success("User counted",countUser));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> userList = authService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("User list",userList));
    }
}
