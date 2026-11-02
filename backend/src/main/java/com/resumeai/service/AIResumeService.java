
package com.resumeai.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.stereotype.Service;

@Service
public class AIResumeService {

    private final OpenAIClient client;

    public AIResumeService(OpenAIClient client) {
        this.client = client;
    }

    public String analyzeResume(String resumeText) {

        String prompt = """
            You are an expert professional resume reviewer.

            Analyze the following resume and provide:

            1. Overall resume quality score out of 100.
            2. Professional summary feedback.
            3. Technical skills analysis.
            4. Key strengths.
            5. Weaknesses and missing information.
            6. Important ATS keywords to add.
            7. Five actionable improvements.

            Be honest, specific and constructive.
            Do not invent qualifications or experience.

            Resume:
            """ + resumeText;

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_5_2)
                .input(prompt)
                .build();

        Response response = client.responses().create(params);

        return response.output().stream()
        .flatMap(item -> item.message().stream())
        .flatMap(message -> message.content().stream())
        .flatMap(content -> content.outputText().stream())
        .map(outputText -> outputText.text())
        .collect(java.util.stream.Collectors.joining("\n"));
    }
}
