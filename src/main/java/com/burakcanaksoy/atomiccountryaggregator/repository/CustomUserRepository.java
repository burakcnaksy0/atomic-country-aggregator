package com.burakcanaksoy.atomiccountryaggregator.repository;

import com.burakcanaksoy.atomiccountryaggregator.request.RegisterRequest;
import com.burakcanaksoy.atomiccountryaggregator.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CustomUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomUserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(RegisterRequest registerRequest){
        String sql = "INSERT INTO users (name,surname,username,email,password,user_role,created_at) VALUES(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                registerRequest.getName(),
                registerRequest.getSurname(),
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                "ROLE_USER",
                LocalDateTime.now()
        );
    }

    public int countUser(){
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public List<User> getAllUsers(){
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }
}
