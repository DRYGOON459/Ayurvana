package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSearchResultResponse {

    private String query;
    private int totalMatches;

    @Builder.Default
    private List<SearchResultItem> herbs = new ArrayList<>();

    @Builder.Default
    private List<SearchResultItem> yoga = new ArrayList<>();

    @Builder.Default
    private List<SearchResultItem> ayurveda = new ArrayList<>();

    @Builder.Default
    private List<SearchResultItem> articles = new ArrayList<>();
}
