package com.ayurvana.repository;

import com.ayurvana.model.entity.Order;
import com.ayurvana.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<Order> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
