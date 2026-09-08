package com.ayurvana.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoshaQuizEvaluationRequest {
    
    @NotEmpty(message = "Selected option IDs cannot be empty")
    private List<Long> selectedOptionIds;
}
