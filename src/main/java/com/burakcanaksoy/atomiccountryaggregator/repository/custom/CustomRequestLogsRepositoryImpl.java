package com.burakcanaksoy.atomiccountryaggregator.repository.custom;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import com.burakcanaksoy.atomiccountryaggregator.repository.CustomRequestLogsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomRequestLogsRepositoryImpl implements CustomRequestLogsRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomRequestLogsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<RequestLogs> rowMapper = (rs, rowNum) -> RequestLogs.builder()
            .id(rs.getLong("id"))
            .username(rs.getString("username"))
            .method(rs.getString("method"))
            .url(rs.getString("url"))
            .requestBody(rs.getString("request_body"))
            .responseBody(rs.getString("response_body"))
            .status(rs.getInt("status"))
            .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
            .executionTimeMs(rs.getLong("execution_time_ms"))
            .build();

    @Override
    public List<RequestLogs> findByUsername(String username) {
        String sql = "SELECT * FROM request_logs WHERE username = ?";
        return jdbcTemplate.query(sql, rowMapper, username);
    }

    @Override
    public List<RequestLogs> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM request_logs WHERE timestamp BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, rowMapper, Timestamp.valueOf(start), Timestamp.valueOf(end));
    }

    public List<RequestLogs> findByUsernameAndTimestampBetween(String username, LocalDateTime start,
            LocalDateTime end) {
        String sql = "SELECT * FROM request_logs WHERE username = ? AND timestamp BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, rowMapper, username, Timestamp.valueOf(start), Timestamp.valueOf(end));
    }
}
