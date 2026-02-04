package com.burakcanaksoy.atomiccountryaggregator.filter;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import com.burakcanaksoy.atomiccountryaggregator.repository.RequestLogsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Component
@Order(2)
public class LoggingFilter extends OncePerRequestFilter {
    private final RequestLogsRepository logRepository;
    private final ObjectMapper objectMapper;

    // log yapılmayacak endpointler
    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/api/v1/request/");

    public LoggingFilter(RequestLogsRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (EXCLUDED_PATHS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, 50 * 1024 * 1024);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String username = "Anonymous";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
                username = auth.getName();
            }
            String reqBody = maskSensitiveData(
                    new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
            String resBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

            RequestLogs log = RequestLogs.builder()
                    .username(username)
                    .method(request.getMethod())
                    .url(request.getRequestURI())
                    .status(response.getStatus())
                    .requestBody(reqBody)
                    .responseBody(resBody)
                    .timestamp(LocalDateTime.now())
                    .executionTimeMs(duration)
                    .build();

            logRepository.save(log);
            responseWrapper.copyBodyToResponse();
        }
    }

    // Loglarda yer alan password bilgisini maskelemek
    private String maskSensitiveData(String content) {
        try {
            Map<String, Object> map = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
            if (map.containsKey("password")) {
                map.put("password", "********");
                return objectMapper.writeValueAsString(map);
            }
            return content;
        } catch (Exception e) {
            return content;
        }
    }
}
