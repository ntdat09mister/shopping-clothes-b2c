package com.orderservice.service;

import com.orderservice.domain.dto.input.CartUpdateDTO;
import com.orderservice.domain.dto.input.CartInsertDTO;
import com.orderservice.domain.dto.output.CartDTO;
import com.orderservice.domain.dto.output.CartOutput;
import com.orderservice.domain.dto.output.CartView;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CartService {
    void update(CartUpdateDTO cart);

    CartDTO findAll(Long userId, int pageNo,int pageSize,String sortBy,String sortDir);

    void deleteById(Long id);

    void deleteByUserId(Long userId);

    void insert(CartInsertDTO cart);

    CartView findCartViewById(Long id);

    CartOutput findAllCart(Long id, int pageNo, int pageSize, String sortBy, String sortDir);
}