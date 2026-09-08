package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private String sanskritName;
    private String slug;
    private ProductCategory category;
    private DoshaType targetDosha;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stockQuantity;
    private String netWeight;
    private String imageUrl;
    private Boolean isFeatured;
    private Boolean isInStock;
    private BigDecimal rating;
    private Integer reviewCount;
}
