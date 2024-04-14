package com.bohdan.onlinefoodordering.dto.cart;

import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    private Long cartItemId;
    private int quantity;
}
