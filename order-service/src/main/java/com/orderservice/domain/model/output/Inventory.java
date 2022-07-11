package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Long id;

    private Long branchId;

    private Long productId;

    private Long quantity;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
