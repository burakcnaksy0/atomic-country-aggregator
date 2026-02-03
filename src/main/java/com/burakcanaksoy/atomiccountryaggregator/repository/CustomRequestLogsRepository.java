package com.burakcanaksoy.atomiccountryaggregator.repository;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import java.util.List;
import java.time.LocalDateTime;

public interface CustomRequestLogsRepository {
    List<RequestLogs> findByUsername(String username);
    List<RequestLogs> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
