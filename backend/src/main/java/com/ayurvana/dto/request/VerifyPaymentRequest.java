package com.ayurvana.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPaymentRequest {

    @NotBlank(message = "Transaction reference is required")
    private String transactionReference;

    private String gatewayOrderId;

    private String gatewayPaymentId;

    private String gatewaySignature;

    private Boolean simulateSuccess = true;
}
