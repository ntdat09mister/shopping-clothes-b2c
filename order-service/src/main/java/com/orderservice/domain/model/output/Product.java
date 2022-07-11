package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String color;
    private String size;
    private Long productLineId;
    private Long price;
    private Long discountId;
    private Long active;
    private ProductLine productLine;
    private List<Inventory> inventories;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
