package com.orderservice.domain.dto.output;

import com.orderservice.domain.dto.input.CartInsertDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartOutput {
    private List<CartView> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;

}
