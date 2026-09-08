package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "sanskrit_name", length = 150)
    private String sanskritName;

    @Column(unique = true, nullable = false, length = 180)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_dosha", length = 30)
    private DoshaType targetDosha;

    @Column(name = "short_description", length = 300)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "classical_reference", length = 200)
    private String classicalReference;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(columnDefinition = "TEXT")
    private String dosageInstructions;

    @Column(columnDefinition = "TEXT")
    private String anupana; // Vehicle: Warm water, Honey, Milk, Ghee

    @Column(columnDefinition = "TEXT")
    private String contraindications;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    private Integer stockQuantity = 100;

    @Column(name = "net_weight", length = 50)
    private String netWeight; // e.g. "100g", "200ml", "60 Capsules"

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    private Boolean isFeatured = false;

    @Column(name = "is_in_stock", nullable = false)
    @Builder.Default
    private Boolean isInStock = true;

    @Column(precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal rating = BigDecimal.valueOf(4.8);

    @Column(name = "review_count")
    @Builder.Default
    private Integer reviewCount = 24;
}
