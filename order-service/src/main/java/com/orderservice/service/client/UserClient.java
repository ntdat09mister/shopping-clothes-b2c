package com.orderservice.service.client;

import com.orderservice.domain.message.Message;
import com.orderservice.domain.model.output.SaleStaff;
import com.orderservice.domain.model.output.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",url = "http://localhost:8901")
public interface UserClient {
    @GetMapping("user/{id}")
    Message<User> findByUserId(@PathVariable Long id);

    @GetMapping("user/staff/sale/{id}")
    Message<SaleStaff> findByStaffId(@PathVariable Long id);

    @GetMapping("user/q")
    Message<User> findByUserName(@RequestParam("username") String userName);
}
