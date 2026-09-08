package com.ayurvana.service;

import com.ayurvana.dto.request.CreatePaymentIntentRequest;
import com.ayurvana.dto.request.VerifyPaymentRequest;
import com.ayurvana.dto.response.PaymentResponse;
import com.ayurvana.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentResponse createPaymentIntent(CreatePaymentIntentRequest request, User user);
    PaymentResponse verifyPayment(VerifyPaymentRequest request, User user);
    PaymentResponse getPaymentByReference(String reference, User user);
    Page<PaymentResponse> getUserTransactions(User user, Pageable pageable);
}
