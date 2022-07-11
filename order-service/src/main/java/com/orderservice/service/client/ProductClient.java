package com.orderservice.service.client;

import com.orderservice.domain.dto.output.InventoryDTO;
import com.orderservice.domain.message.Message;
import com.orderservice.domain.model.output.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service",url = "http://localhost:8000")
public interface ProductClient {
    @GetMapping("/product/{id}")
    Message<Product> findByProductId(@PathVariable Long id);
    @PostMapping("inventory/quantity")
    void updateQuantities(@RequestBody List<InventoryDTO> inventoryDTOs);

}
