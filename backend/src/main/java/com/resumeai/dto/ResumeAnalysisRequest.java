package com.resumeai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResumeAnalysisRequest(
    @NotBlank(message = "Resume text is required")
    @Size(max = 20000, message = "Resume text must be under 20,000 characters")
    String resumeText
) {}