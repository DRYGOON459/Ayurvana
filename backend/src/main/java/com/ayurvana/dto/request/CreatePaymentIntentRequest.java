package com.ayurvana.dto.request;

import com.ayurvana.model.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentIntentRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Amount must be greater than or equal to 1.00")
    private BigDecimal amount;

    @Builder.Default
    private String currency = "INR";

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String purpose; // "CONSULTATION", "ECOMMERCE_ORDER"

    private Long appointmentId;

    private Long orderId;

    private String metadataJson;
}
