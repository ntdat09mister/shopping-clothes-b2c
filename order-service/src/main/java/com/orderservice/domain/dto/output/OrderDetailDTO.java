package com.orderservice.domain.dto.output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long orderId;
    private Long status;
    private List<ProductOrderDetailDTO> productOrderDetailDTOS;
    private LocalDateTime createdAt;
    private Long totalAmount;
    private Long paymentId;
    private UserOrderDetailDTO userOrderDetailDTO;
}
