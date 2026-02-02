package com.burakcanaksoy.atomiccountryaggregator.service.impl;

import com.burakcanaksoy.atomiccountryaggregator.mapper.UserMapper;
import com.burakcanaksoy.atomiccountryaggregator.response.AuthResponse;
import com.burakcanaksoy.atomiccountryaggregator.request.LoginRequest;
import com.burakcanaksoy.atomiccountryaggregator.request.RegisterRequest;
import com.burakcanaksoy.atomiccountryaggregator.exception.InvalidCredentialsException;
import com.burakcanaksoy.atomiccountryaggregator.model.User;
import com.burakcanaksoy.atomiccountryaggregator.model.enums.Role;
import com.burakcanaksoy.atomiccountryaggregator.repository.CustomUserRepository;
import com.burakcanaksoy.atomiccountryaggregator.repository.UserRepository;
import com.burakcanaksoy.atomiccountryaggregator.response.UserResponse;
import com.burakcanaksoy.atomiccountryaggregator.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserRepository customUserRepository;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,
                       JwtService jwtService,AuthenticationManager authenticationManager,
                       CustomUserRepository customUserRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserRepository = customUserRepository;
    }

    public void register(RegisterRequest registerRequest) {
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

    public AuthResponse login(LoginRequest loginRequest) {
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

    public void registerWithSql(RegisterRequest registerRequest){
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        int result = customUserRepository.insertUser(registerRequest);

        if (result == 0){
            throw new RuntimeException("User not created");
        }

        AuthResponse.builder()
                .token(null)
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .message("Registered successfully")
                .build();
    }

    public int countUser() {
        return customUserRepository.countUser();
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = customUserRepository.getAllUsers();
        return UserMapper.toListResponse(userList);

    }
}
