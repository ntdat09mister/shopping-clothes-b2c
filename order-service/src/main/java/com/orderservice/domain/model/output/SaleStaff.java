package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleStaff {
    private Long id;
    private String name;
    private boolean active;
}
