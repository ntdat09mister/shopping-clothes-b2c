package com.orderservice.domain.dto.output;

import com.orderservice.domain.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Page<Cart> cartData;
    private Long totalCarts;
}
