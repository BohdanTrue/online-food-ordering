package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.cart.AddCartItemRequestDto;
import com.bohdan.onlinefoodordering.dto.cart.UpdateCartItemRequestDto;
import com.bohdan.onlinefoodordering.model.Cart;
import com.bohdan.onlinefoodordering.model.CartItem;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.service.CartService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequestDto requestDto,
            Authentication authentication
            ){
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        CartItem cartItem = cartService.addItemToCart(requestDto, user.getId());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody UpdateCartItemRequestDto requestDto){
        CartItem cartItem = cartService.updateCartItemQuantity(requestDto.getCartItemId(), requestDto.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
            @PathVariable Long id,
            Authentication authentication
    ){
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Cart cart = cartService.removeItemFromCart(id, user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> removeCartItem(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Cart cart = cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
