package com.ayurvana.service.impl;

import com.ayurvana.dto.request.CreatePaymentIntentRequest;
import com.ayurvana.dto.request.VerifyPaymentRequest;
import com.ayurvana.dto.response.PaymentResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Order;
import com.ayurvana.model.entity.PaymentTransaction;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.OrderStatus;
import com.ayurvana.model.enums.PaymentStatus;
import com.ayurvana.repository.OrderRepository;
import com.ayurvana.repository.PaymentTransactionRepository;
import com.ayurvana.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public PaymentResponse createPaymentIntent(CreatePaymentIntentRequest request, User user) {
        log.info("Creating payment intent for user: {}, amount: {}, method: {}", user.getEmail(), request.getAmount(), request.getPaymentMethod());

        String gatewayOrderId = "order_sim_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        PaymentTransaction transaction = PaymentTransaction.builder()
                .user(user)
                .amount(request.getAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "INR")
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .purpose(request.getPurpose() != null ? request.getPurpose() : "GENERAL")
                .appointmentId(request.getAppointmentId())
                .orderId(request.getOrderId())
                .gatewayOrderId(gatewayOrderId)
                .metadataJson(request.getMetadataJson())
                .build();

        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public PaymentResponse verifyPayment(VerifyPaymentRequest request, User user) {
        log.info("Verifying payment transaction: {}", request.getTransactionReference());

        PaymentTransaction transaction = paymentTransactionRepository.findByTransactionReference(request.getTransactionReference())
                .orElseThrow(() -> new ResourceNotFoundException("PaymentTransaction", "reference", request.getTransactionReference()));

        boolean isSuccess = request.getSimulateSuccess() != null ? request.getSimulateSuccess() : true;

        if (isSuccess) {
            transaction.setStatus(PaymentStatus.COMPLETED);
            transaction.setGatewayPaymentId(request.getGatewayPaymentId() != null ? request.getGatewayPaymentId() : "pay_sim_" + UUID.randomUUID().toString().substring(0, 12));
            transaction.setGatewaySignature(request.getGatewaySignature() != null ? request.getGatewaySignature() : "sig_" + UUID.randomUUID().toString().substring(0, 16));

            // If linked to an e-commerce order, update order status
            if (transaction.getOrderId() != null) {
                orderRepository.findById(transaction.getOrderId()).ifPresent(order -> {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    order.setOrderStatus(OrderStatus.CONFIRMED);
                    order.setPaymentTransaction(transaction);
                    orderRepository.save(order);
                });
            }
        } else {
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setFailureReason("Payment declined or signature mismatch.");
        }

        PaymentTransaction updated = paymentTransactionRepository.save(transaction);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByReference(String reference, User user) {
        PaymentTransaction transaction = paymentTransactionRepository.findByTransactionReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentTransaction", "reference", reference));
        return mapToResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getUserTransactions(User user, Pageable pageable) {
        return paymentTransactionRepository.findByUserOrderByCreatedAtDesc(user, pageable)
                .map(this::mapToResponse);
    }

    private PaymentResponse mapToResponse(PaymentTransaction t) {
        return PaymentResponse.builder()
                .id(t.getId())
                .transactionReference(t.getTransactionReference())
                .gatewayOrderId(t.getGatewayOrderId())
                .gatewayPaymentId(t.getGatewayPaymentId())
                .amount(t.getAmount())
                .currency(t.getCurrency())
                .status(t.getStatus())
                .paymentMethod(t.getPaymentMethod())
                .purpose(t.getPurpose())
                .appointmentId(t.getAppointmentId())
                .orderId(t.getOrderId())
                .failureReason(t.getFailureReason())
                .createdAt(t.getCreatedAt())
                .receiptUrl("/api/v1/payments/receipt/" + t.getTransactionReference())
                .build();
    }
}
