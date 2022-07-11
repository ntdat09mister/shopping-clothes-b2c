package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLine {
    private Long id;
    private String name;
    private String desc;
    private Long categoryId;
    private Long discountId;
    private Long active;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
