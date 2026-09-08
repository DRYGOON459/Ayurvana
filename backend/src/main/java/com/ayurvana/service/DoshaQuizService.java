package com.ayurvana.service;

import com.ayurvana.dto.request.DoshaQuizEvaluationRequest;
import com.ayurvana.dto.response.DoshaQuizEvaluationResponse;
import com.ayurvana.dto.response.DoshaQuizQuestionResponse;

import java.util.List;

public interface DoshaQuizService {
    List<DoshaQuizQuestionResponse> getAllQuestions();
    DoshaQuizEvaluationResponse evaluateQuiz(DoshaQuizEvaluationRequest request, String userEmail);
}
