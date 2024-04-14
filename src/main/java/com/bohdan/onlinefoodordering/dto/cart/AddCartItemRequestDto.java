package com.bohdan.onlinefoodordering.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequestDto {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
