package com.burakcanaksoy.atomiccountryaggregator.controller;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import com.burakcanaksoy.atomiccountryaggregator.response.ApiResponse;
import com.burakcanaksoy.atomiccountryaggregator.service.RequestService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/request")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<RequestLogs>>> getAllRequests() {
        return ResponseEntity.ok(ApiResponse.success("All requests are brought.", requestService.getAllRequests()));
    }

    @GetMapping("/filter/username")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<RequestLogs>>> searchLogWithUsername(
            @RequestParam(required = false) String username) {
        return ResponseEntity.ok(
                ApiResponse.success(username + " request filters.", requestService.searchLogsWithUsername(username)));
    }

    @GetMapping("/filter/date-range")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<RequestLogs>>> searchLogWithTimestamp(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(ApiResponse.success(startDate + " - " + endDate,
                requestService.searchLogsWithTimestamp(startDate, endDate)));
    }
}