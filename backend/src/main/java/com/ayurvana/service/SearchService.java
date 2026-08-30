package com.ayurvana.service;

import com.ayurvana.dto.response.GlobalSearchResultResponse;

public interface SearchService {

    GlobalSearchResultResponse globalSearch(String query, int limitPerCategory);
}
