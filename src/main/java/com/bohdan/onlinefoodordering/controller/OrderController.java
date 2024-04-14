package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.order.OrderRequestDto;
import com.bohdan.onlinefoodordering.model.Order;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.service.OrderService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequestDto requestDto,
            Authentication authentication
        ) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Order order = orderService.create(requestDto, user.getId());

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        List<Order> usersOrders = orderService.getUsersOrders(user.getId());

        return new ResponseEntity<>(usersOrders, HttpStatus.OK);
    }
}
