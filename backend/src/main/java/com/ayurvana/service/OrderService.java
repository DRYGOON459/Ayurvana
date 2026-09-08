package com.ayurvana.service;

import com.ayurvana.dto.request.CreateOrderRequest;
import com.ayurvana.dto.response.OrderResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request, User user);
    OrderResponse getOrderByNumber(String orderNumber, User user);
    PagedResponse<OrderResponse> getUserOrders(User user, Pageable pageable);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
