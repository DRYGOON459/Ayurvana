package com.ayurvana.model.entity;

import com.ayurvana.model.enums.PaymentMethod;
import com.ayurvana.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_reference", unique = true, nullable = false)
    private String transactionReference;

    @Column(name = "gateway_order_id")
    private String gatewayOrderId;

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "gateway_signature")
    private String gatewaySignature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 30)
    private PaymentMethod paymentMethod;

    @Column(name = "purpose", length = 50)
    private String purpose; // "CONSULTATION", "ECOMMERCE_ORDER", "WELLNESS_SUBSCRIPTION"

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    @Column(name = "failure_reason")
    private String failureReason;

    @PrePersist
    public void generateTransactionRef() {
        if (this.transactionReference == null) {
            this.transactionReference = "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
