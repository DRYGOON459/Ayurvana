package com.ayurvana.service;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.ProductDetailResponse;
import com.ayurvana.dto.response.ProductSummaryResponse;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    PagedResponse<ProductSummaryResponse> getAllProducts(Pageable pageable);
    PagedResponse<ProductSummaryResponse> getProductsByCategory(ProductCategory category, Pageable pageable);
    PagedResponse<ProductSummaryResponse> getProductsByDosha(DoshaType targetDosha, Pageable pageable);
    PagedResponse<ProductSummaryResponse> searchProducts(String query, Pageable pageable);
    List<ProductSummaryResponse> getFeaturedProducts();
    ProductDetailResponse getProductBySlug(String slug);
}
