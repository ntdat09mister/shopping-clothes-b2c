package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private Long id;
    private String name;
    private Long addressId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
