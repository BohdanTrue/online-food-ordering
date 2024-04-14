package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.dto.cart.AddCartItemRequestDto;
import com.bohdan.onlinefoodordering.model.Cart;
import com.bohdan.onlinefoodordering.model.CartItem;
import com.bohdan.onlinefoodordering.model.User;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequestDto requestDto, Long userId);

    CartItem updateCartItemQuantity(Long cartItemId, int quantity);

    Cart removeItemFromCart(Long cartItemId, Long userId);

    Long calculateCartTotals(Cart cart);

    Cart findById(Long cartId);

    Cart findCartByUserId(Long userId);

    Cart clearCart(Long userId);
}
