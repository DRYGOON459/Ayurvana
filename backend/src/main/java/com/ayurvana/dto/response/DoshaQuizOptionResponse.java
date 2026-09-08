package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DoshaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoshaQuizOptionResponse {
    private Long id;
    private String optionText;
    private DoshaType doshaType;
    private Integer scoreWeight;
    private Integer optionOrder;
}
