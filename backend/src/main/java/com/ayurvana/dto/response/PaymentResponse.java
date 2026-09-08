package com.ayurvana.dto.response;

import com.ayurvana.model.enums.PaymentMethod;
import com.ayurvana.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private String transactionReference;
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String purpose;
    private Long appointmentId;
    private Long orderId;
    private String failureReason;
    private Instant createdAt;
    private String receiptUrl;
}
