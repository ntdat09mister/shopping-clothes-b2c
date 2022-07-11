package com.orderservice.domain.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long orderId;
    private Long status;
    private List<ProductOrderDTO> productOrderDTOS;
    private Long orderTotalAmount;
}
