package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.dto.order.OrderRequestDto;
import com.bohdan.onlinefoodordering.model.Address;
import com.bohdan.onlinefoodordering.model.Cart;
import com.bohdan.onlinefoodordering.model.CartItem;
import com.bohdan.onlinefoodordering.model.Order;
import com.bohdan.onlinefoodordering.model.OrderItem;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.repository.AddressRepository;
import com.bohdan.onlinefoodordering.repository.OrderItemRepository;
import com.bohdan.onlinefoodordering.repository.OrderRepository;
import com.bohdan.onlinefoodordering.repository.UserRepository;
import com.bohdan.onlinefoodordering.service.CartService;
import com.bohdan.onlinefoodordering.service.OrderService;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import com.bohdan.onlinefoodordering.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final UserService userService;


    @Override
    public Order create(OrderRequestDto requestDto, Long userId) {
        User user = userService.findUserById(userId);

        Address shippingAddress = requestDto.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shippingAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findById(requestDto.getRestaurantId());

        Order order = new Order();
        order.setCustomer(user);
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");
        order.setDeliveryAddress(savedAddress);
        order.setRestaurant(restaurant);


        Cart cart = cartService.findCartByUserId(userId);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);

        return savedOrder;
    }

    @Override
    public Order update(Long orderId, String orderStatus) throws Exception {
        Order order = findById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Cannot update order. Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrders(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrders(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .toList();
        }

        return orders;
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order by id: " + orderId));
    }
}
