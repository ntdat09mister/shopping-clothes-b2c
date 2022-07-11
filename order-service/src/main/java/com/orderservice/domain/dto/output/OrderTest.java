package com.orderservice.domain.dto.output;

import com.orderservice.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderTest {
    Page<Order> orders;
}
