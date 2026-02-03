package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("request_logs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogs {
    @Id
    private Long id;
    private String username;
    private String method;
    private String url;
    private String requestBody;
    private String responseBody;
    private int status;
    private LocalDateTime timestamp;
    private Long executionTimeMs;
}
