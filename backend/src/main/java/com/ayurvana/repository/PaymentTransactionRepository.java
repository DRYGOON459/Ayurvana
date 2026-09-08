package com.ayurvana.repository;

import com.ayurvana.model.entity.PaymentTransaction;
import com.ayurvana.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByTransactionReference(String transactionReference);
    Optional<PaymentTransaction> findByGatewayOrderId(String gatewayOrderId);
    Page<PaymentTransaction> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<PaymentTransaction> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
