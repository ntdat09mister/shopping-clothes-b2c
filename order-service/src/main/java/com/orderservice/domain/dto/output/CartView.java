package com.orderservice.domain.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartView {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
    private Long amount;
}
