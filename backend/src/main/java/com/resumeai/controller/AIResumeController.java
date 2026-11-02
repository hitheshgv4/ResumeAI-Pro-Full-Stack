package com.resumeai.controller;

import com.resumeai.dto.ResumeAnalysisRequest;
import com.resumeai.service.AIResumeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIResumeController {

    private final AIResumeService aiResumeService;

    public AIResumeController(AIResumeService aiResumeService) {
        this.aiResumeService = aiResumeService;
    }

    @PostMapping("/analyze-resume")
    public ResponseEntity<?> analyzeResume(
            @Valid @RequestBody ResumeAnalysisRequest request) {

        String result = aiResumeService.analyzeResume(
                request.resumeText()
        );

        return ResponseEntity.ok(Map.of("analysis", result));
    }
}