package com.orderservice.service;

import com.orderservice.domain.dto.input.CartUpdateDTO;
import com.orderservice.domain.dto.input.CartInsertDTO;
import com.orderservice.domain.dto.output.CartDTO;


public interface CartService {
    void update(CartUpdateDTO cart);

    CartDTO findAll(Long userId, int pageNo,int pageSize,String sortBy,String sortDir);

    void deleteById(Long id);

    void deleteByUserId(Long userId);

    void insert(CartInsertDTO cart);

}