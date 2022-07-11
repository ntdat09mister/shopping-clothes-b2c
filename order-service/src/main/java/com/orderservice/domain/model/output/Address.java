package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;
    private Long province;
    private Long district;
    private Long street;
    private Long ward;
    private String specificAddress;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
