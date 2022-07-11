package com.orderservice.domain.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderDetailDTO {
    private Long quantity;
    private String name;
    private String color;
    private String size;
    private Long amount;
}
