package com.ayurvana.dto.response;

import com.ayurvana.model.enums.OrderStatus;
import com.ayurvana.model.enums.PaymentMethod;
import com.ayurvana.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String couponCode;
    private String recipientName;
    private String phoneNumber;
    private String streetAddress;
    private String city;
    private String state;
    private String pincode;
    private String trackingNumber;
    private String notes;
    private List<OrderItemResponse> items;
    private String paymentTransactionReference;
    private Instant createdAt;
}
