package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.dto.order.OrderRequestDto;
import com.bohdan.onlinefoodordering.model.Order;

import java.util.List;

public interface OrderService {
    Order create(OrderRequestDto requestDto, Long userId);

    Order update(Long orderId, String orderStatus) throws Exception;

    void cancelOrder(Long orderId);

    List<Order> getUsersOrders(Long userId);

    List<Order> getRestaurantsOrders(Long restaurantId, String orderStatus);

    Order findById(Long orderId);
}
