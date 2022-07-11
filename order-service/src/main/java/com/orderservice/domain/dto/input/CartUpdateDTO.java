package com.orderservice.domain.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateDTO {
    @NotNull(message = "Cart ID not be null")
    private Long id;
    @NotNull(message = "User ID not be null")
    private Long userId;
    @NotNull(message = "Product ID not be null")
    private Long productId;
    @NotNull(message = "Quantity not be null")
    @Min(value = 1,message = "Quantity must be more than one!")
    private Long quantity;
    @NotNull(message = "Active is true or false")
    private Boolean active;
}
