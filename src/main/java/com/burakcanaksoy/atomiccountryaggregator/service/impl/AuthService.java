package com.burakcanaksoy.atomiccountryaggregator.service.impl;

import com.burakcanaksoy.atomiccountryaggregator.dto.AuthResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.LoginRequest;
import com.burakcanaksoy.atomiccountryaggregator.dto.RegisterRequest;
import com.burakcanaksoy.atomiccountryaggregator.exception.InvalidCredentialsException;
import com.burakcanaksoy.atomiccountryaggregator.model.User;
import com.burakcanaksoy.atomiccountryaggregator.model.enums.Role;
import com.burakcanaksoy.atomiccountryaggregator.repository.UserRepository;
import com.burakcanaksoy.atomiccountryaggregator.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager = authenticationManager;
    }


    public void register(@Valid RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("This username already exists.");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("This email already exists");
        }
        var user = User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        AuthResponse.builder()
                .token(null)
                .username(user.getUsername())
                .email(user.getEmail())
                .message("Registered successfully")
                .build();
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Username or password is incorrect");
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Authentication failed: " + e.getMessage());
        }

        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .message("Login successfully")
                .build();
    }
}
