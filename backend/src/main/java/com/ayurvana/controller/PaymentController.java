package com.ayurvana.controller;

import com.ayurvana.dto.request.CreatePaymentIntentRequest;
import com.ayurvana.dto.request.VerifyPaymentRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.PaymentResponse;
import com.ayurvana.model.entity.User;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping("/create-intent")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPaymentIntent(
            @Valid @RequestBody CreatePaymentIntentRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentResponse response = paymentService.createPaymentIntent(request, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Payment intent created successfully"));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentResponse response = paymentService.verifyPayment(request, user);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment verified successfully"));
    }

    @GetMapping("/{reference}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByReference(
            @PathVariable String reference,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentResponse response = paymentService.getPaymentByReference(reference, user);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment transaction retrieved"));
    }

    @GetMapping("/user/transactions")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getUserTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Page<PaymentResponse> transactions = paymentService.getUserTransactions(user, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(transactions, "User transactions retrieved"));
    }
}
