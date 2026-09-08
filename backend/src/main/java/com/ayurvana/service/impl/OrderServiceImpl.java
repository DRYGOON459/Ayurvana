package com.ayurvana.service.impl;

import com.ayurvana.dto.request.CreateOrderRequest;
import com.ayurvana.dto.request.OrderItemRequest;
import com.ayurvana.dto.response.OrderItemResponse;
import com.ayurvana.dto.response.OrderResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.BadRequestException;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Order;
import com.ayurvana.model.entity.OrderItem;
import com.ayurvana.model.entity.Product;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.OrderStatus;
import com.ayurvana.model.enums.PaymentMethod;
import com.ayurvana.model.enums.PaymentStatus;
import com.ayurvana.repository.OrderRepository;
import com.ayurvana.repository.ProductRepository;
import com.ayurvana.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, User user) {
        log.info("Creating order for user: {}, items count: {}", user.getEmail(), request.getItems().size());

        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemReq.getProductId()));

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal unitPrice = product.getDiscountPrice() != null ? product.getDiscountPrice() : product.getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            subtotal = subtotal.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .productImageUrl(product.getImageUrl())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(itemTotal)
                    .build();

            items.add(orderItem);

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - itemReq.getQuantity());
            if (product.getStockQuantity() <= 0) {
                product.setIsInStock(false);
            }
            productRepository.save(product);
        }

        // Apply discount coupon if applicable
        BigDecimal discount = BigDecimal.ZERO;
        if (request.getCouponCode() != null && !request.getCouponCode().isBlank()) {
            String code = request.getCouponCode().trim().toUpperCase();
            if ("AYUR15".equals(code)) {
                discount = subtotal.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.HALF_UP);
            } else if ("VEDIC20".equals(code)) {
                discount = subtotal.multiply(new BigDecimal("0.20")).setScale(2, RoundingMode.HALF_UP);
            }
        }

        BigDecimal shippingFee = subtotal.compareTo(new BigDecimal("500")) >= 0 ? BigDecimal.ZERO : new BigDecimal("50.00");
        BigDecimal taxAmount = subtotal.subtract(discount).multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.subtract(discount).add(shippingFee).add(taxAmount).setScale(2, RoundingMode.HALF_UP);

        PaymentStatus initPaymentStatus = request.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY ?
                PaymentStatus.PENDING : PaymentStatus.PENDING;

        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.CONFIRMED)
                .paymentStatus(initPaymentStatus)
                .paymentMethod(request.getPaymentMethod())
                .subtotalAmount(subtotal)
                .discountAmount(discount)
                .shippingFee(shippingFee)
                .taxAmount(taxAmount)
                .totalAmount(total)
                .couponCode(request.getCouponCode())
                .recipientName(request.getRecipientName())
                .phoneNumber(request.getPhoneNumber())
                .streetAddress(request.getStreetAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .notes(request.getNotes())
                .build();

        for (OrderItem item : items) {
            order.addItem(item);
        }

        Order saved = orderRepository.save(order);
        log.info("Order placed successfully with orderNumber: {}", saved.getOrderNumber());
        return mapToOrderResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderByNumber(String orderNumber, User user) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
        return mapToOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<OrderResponse> getUserOrders(User user, Pageable pageable) {
        Page<Order> page = orderRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        List<OrderResponse> content = page.getContent().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());

        return PagedResponse.<OrderResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        order.setOrderStatus(status);
        if (status == OrderStatus.DELIVERED && order.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        Order updated = orderRepository.save(order);
        return mapToOrderResponse(updated);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                        .productName(item.getProductName())
                        .productImageUrl(item.getProductImageUrl())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .subtotalAmount(order.getSubtotalAmount())
                .discountAmount(order.getDiscountAmount())
                .shippingFee(order.getShippingFee())
                .taxAmount(order.getTaxAmount())
                .totalAmount(order.getTotalAmount())
                .couponCode(order.getCouponCode())
                .recipientName(order.getRecipientName())
                .phoneNumber(order.getPhoneNumber())
                .streetAddress(order.getStreetAddress())
                .city(order.getCity())
                .state(order.getState())
                .pincode(order.getPincode())
                .trackingNumber(order.getTrackingNumber())
                .notes(order.getNotes())
                .items(itemResponses)
                .paymentTransactionReference(order.getPaymentTransaction() != null ? order.getPaymentTransaction().getTransactionReference() : null)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
