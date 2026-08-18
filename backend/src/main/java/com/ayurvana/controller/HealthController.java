package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHealthStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "AyurVana Backend API");
        status.put("version", "1.0.0");
        status.put("environment", "Development");

        return ResponseEntity.ok(ApiResponse.success(status, "AyurVana backend is operational and healthy"));
    }
}
