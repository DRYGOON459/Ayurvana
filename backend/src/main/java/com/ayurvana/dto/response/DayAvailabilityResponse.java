package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayAvailabilityResponse {

    private LocalDate date;
    private int dayOfWeek;

    @Builder.Default
    private List<SlotResponse> slots = new ArrayList<>();
}
