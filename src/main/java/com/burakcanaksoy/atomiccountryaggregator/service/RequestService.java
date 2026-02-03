package com.burakcanaksoy.atomiccountryaggregator.service;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestService {
    List<RequestLogs> getAllRequests();
    List<RequestLogs> searchLogsWithUsername(String username);
    List<RequestLogs> searchLogsWithTimestamp(LocalDateTime startDate,LocalDateTime endDate);
}
