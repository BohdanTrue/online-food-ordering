package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.dto.cart.AddCartItemRequestDto;
import com.bohdan.onlinefoodordering.model.Cart;
import com.bohdan.onlinefoodordering.model.CartItem;
import com.bohdan.onlinefoodordering.model.Food;
import com.bohdan.onlinefoodordering.repository.CartItemRepository;
import com.bohdan.onlinefoodordering.repository.CartRepository;
import com.bohdan.onlinefoodordering.service.CartService;
import com.bohdan.onlinefoodordering.service.FoodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequestDto requestDto, Long userId) {
        Food food = foodService.findById(requestDto.getFoodId());

        Cart cart = cartRepository.findByCustomerId(userId);

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + requestDto.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(requestDto.getQuantity());
        newCartItem.setIngredients(requestDto.getIngredients());
        newCartItem.setTotalPrice(requestDto.getQuantity() + food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find cart item by id: " + cartItemId));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, Long userId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find cart item by id: " + cartItemId));

        Cart cart = cartRepository.findByCustomerId(userId);

        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) {
        return cart.getItems()
                .stream()
                .mapToLong(item -> item.getFood().getPrice() + item.getQuantity())
                .sum();
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find cart by id: " + cartId));
    }

    @Override
    public Cart findCartByUserId(Long userId) {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) {
        Cart cart = findCartByUserId(userId);

        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
