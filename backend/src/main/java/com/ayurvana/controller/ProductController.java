package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.ProductDetailResponse;
import com.ayurvana.dto.response.ProductSummaryResponse;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import com.ayurvana.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ProductSummaryResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<ProductSummaryResponse> response = productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Products retrieved successfully"));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getFeaturedProducts() {
        List<ProductSummaryResponse> featured = productService.getFeaturedProducts();
        return ResponseEntity.ok(ApiResponse.success(featured, "Featured products retrieved"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<PagedResponse<ProductSummaryResponse>>> getProductsByCategory(
            @PathVariable ProductCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        PagedResponse<ProductSummaryResponse> response = productService.getProductsByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Products by category retrieved"));
    }

    @GetMapping("/dosha/{dosha}")
    public ResponseEntity<ApiResponse<PagedResponse<ProductSummaryResponse>>> getProductsByDosha(
            @PathVariable DoshaType dosha,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        PagedResponse<ProductSummaryResponse> response = productService.getProductsByDosha(dosha, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Products by dosha retrieved"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<ProductSummaryResponse>>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<ProductSummaryResponse> response = productService.searchProducts(query, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Search results retrieved"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductBySlug(@PathVariable String slug) {
        ProductDetailResponse response = productService.getProductBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Product detail retrieved"));
    }
}
