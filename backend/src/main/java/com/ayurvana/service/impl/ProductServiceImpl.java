package com.ayurvana.service.impl;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.ProductDetailResponse;
import com.ayurvana.dto.response.ProductSummaryResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Product;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import com.ayurvana.repository.ProductRepository;
import com.ayurvana.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PagedResponse<ProductSummaryResponse> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return buildPagedResponse(page);
    }

    @Override
    public PagedResponse<ProductSummaryResponse> getProductsByCategory(ProductCategory category, Pageable pageable) {
        Page<Product> page = productRepository.findByCategory(category, pageable);
        return buildPagedResponse(page);
    }

    @Override
    public PagedResponse<ProductSummaryResponse> getProductsByDosha(DoshaType targetDosha, Pageable pageable) {
        Page<Product> page = productRepository.findByTargetDosha(targetDosha, pageable);
        return buildPagedResponse(page);
    }

    @Override
    public PagedResponse<ProductSummaryResponse> searchProducts(String query, Pageable pageable) {
        Page<Product> page = productRepository.searchProducts(query, pageable);
        return buildPagedResponse(page);
    }

    @Override
    public List<ProductSummaryResponse> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "slug", slug));
        return mapToDetailResponse(product);
    }

    private PagedResponse<ProductSummaryResponse> buildPagedResponse(Page<Product> page) {
        List<ProductSummaryResponse> content = page.getContent().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());

        return PagedResponse.<ProductSummaryResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private ProductSummaryResponse mapToSummaryResponse(Product p) {
        return ProductSummaryResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .sanskritName(p.getSanskritName())
                .slug(p.getSlug())
                .category(p.getCategory())
                .targetDosha(p.getTargetDosha())
                .shortDescription(p.getShortDescription())
                .price(p.getPrice())
                .discountPrice(p.getDiscountPrice())
                .stockQuantity(p.getStockQuantity())
                .netWeight(p.getNetWeight())
                .imageUrl(p.getImageUrl())
                .isFeatured(p.getIsFeatured())
                .isInStock(p.getIsInStock())
                .rating(p.getRating())
                .reviewCount(p.getReviewCount())
                .build();
    }

    private ProductDetailResponse mapToDetailResponse(Product p) {
        return ProductDetailResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .sanskritName(p.getSanskritName())
                .slug(p.getSlug())
                .category(p.getCategory())
                .targetDosha(p.getTargetDosha())
                .shortDescription(p.getShortDescription())
                .description(p.getDescription())
                .classicalReference(p.getClassicalReference())
                .ingredients(p.getIngredients())
                .benefits(p.getBenefits())
                .dosageInstructions(p.getDosageInstructions())
                .anupana(p.getAnupana())
                .contraindications(p.getContraindications())
                .price(p.getPrice())
                .discountPrice(p.getDiscountPrice())
                .stockQuantity(p.getStockQuantity())
                .netWeight(p.getNetWeight())
                .imageUrl(p.getImageUrl())
                .isFeatured(p.getIsFeatured())
                .isInStock(p.getIsInStock())
                .rating(p.getRating())
                .reviewCount(p.getReviewCount())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
