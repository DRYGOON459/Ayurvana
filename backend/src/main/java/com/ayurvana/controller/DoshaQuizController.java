package com.ayurvana.controller;

import com.ayurvana.dto.request.DoshaQuizEvaluationRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.DoshaQuizEvaluationResponse;
import com.ayurvana.dto.response.DoshaQuizQuestionResponse;
import com.ayurvana.service.DoshaQuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dosha-quiz")
@RequiredArgsConstructor
public class DoshaQuizController {

    private final DoshaQuizService doshaQuizService;

    @GetMapping("/questions")
    public ResponseEntity<ApiResponse<List<DoshaQuizQuestionResponse>>> getQuizQuestions() {
        List<DoshaQuizQuestionResponse> questions = doshaQuizService.getAllQuestions();
        return ResponseEntity.ok(ApiResponse.success(questions, "Dosha quiz questions retrieved successfully"));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ApiResponse<DoshaQuizEvaluationResponse>> evaluateQuiz(
            @Valid @RequestBody DoshaQuizEvaluationRequest request,
            Authentication authentication
    ) {
        String email = authentication != null ? authentication.getName() : null;
        DoshaQuizEvaluationResponse result = doshaQuizService.evaluateQuiz(request, email);
        return ResponseEntity.ok(ApiResponse.success(result, "Dosha constitution evaluated successfully"));
    }
}
