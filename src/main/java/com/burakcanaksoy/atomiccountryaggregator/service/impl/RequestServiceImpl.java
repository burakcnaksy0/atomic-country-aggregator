package com.burakcanaksoy.atomiccountryaggregator.service.impl;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import com.burakcanaksoy.atomiccountryaggregator.repository.RequestLogsRepository;
import com.burakcanaksoy.atomiccountryaggregator.service.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestLogsRepository logsRepository;

    public RequestServiceImpl(RequestLogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Override
    public List<RequestLogs> getAllRequests() {
        return logsRepository.findAll();
    }

    @Override
    public List<RequestLogs> searchLogsWithUsername(String username) {
        if (username != null && !username.isEmpty()) {
            return logsRepository.findByUsername(username);
        }
        return getAllRequests();
    }

    @Override
    public List<RequestLogs> searchLogsWithTimestamp(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return logsRepository.findByTimestampBetween(startDate, endDate);
        }
        return getAllRequests();
    }
}
