package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.order.OrderRequestDto;
import com.bohdan.onlinefoodordering.model.Order;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.service.OrderService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminOrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/order/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getRestaurantOrders(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String order_status
        ) {

        List<Order> restaurantsOrders = orderService.getRestaurantsOrders(restaurantId, order_status);

        return new ResponseEntity<>(restaurantsOrders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String orderStatus
    ) throws Exception {

        Order updatedOrder = orderService.update(orderId, orderStatus);

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
