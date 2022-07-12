package com.orderservice.service.impl;

import com.orderservice.domain.dto.input.CartUpdateDTO;
import com.orderservice.domain.dto.input.CartInsertDTO;
import com.orderservice.domain.dto.output.CartDTO;
import com.orderservice.domain.dto.output.CartOutput;
import com.orderservice.domain.dto.output.CartView;
import com.orderservice.domain.entity.Cart;
import com.orderservice.domain.model.output.Product;
import com.orderservice.exception.CartNotFoundException;
import com.orderservice.exception.QuantityOrderException;
import com.orderservice.repository.CartRepository;
import com.orderservice.repository.root.Param;
import com.orderservice.service.CartService;
import com.orderservice.service.client.ProductClient;
import com.orderservice.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;
    @Override
    public CartDTO findAll(Long userId, int pageNo, int pageSize,String sortBy,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Cart> carts = cartRepository.findByUserIdAndActive(userId, true, pageable);
        Long totalOrders= Long.valueOf(carts.getContent().size());

        return CartDTO.builder()
                .cartData(carts)
                .totalCarts(totalOrders)
                .build();
    }

    @Override
    public void update(CartUpdateDTO cartUpdateDTO) {
        Cart cart = cartRepository.findById(cartUpdateDTO.getId()).orElseThrow(() -> new CartNotFoundException("Cart Id not exist"));
        cart.setUserId(cartUpdateDTO.getUserId());
        cart.setProductId(cartUpdateDTO.getProductId());
        cart.setQuantity(cartUpdateDTO.getQuantity());
        cart.setActive(cartUpdateDTO.getActive());
        cartRepository.save(cart);
    } //Update Cart

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }//Xóa cart theo cartId

    @Override
    public void deleteByUserId(Long userId) {
        cartRepository.deleteByUserId(userId);
    }//Xóa cart theo userId

    @Override
    public void insert(CartInsertDTO cartInsertDTO) {
        Cart cart = new Cart();
        List<Long> inventoryQuantities =  productClient.findByProductId(cartInsertDTO.getProductId()).getData().getInventories().stream().map(inventory -> {
            Long quantity = inventory.getQuantity();
            return quantity;
        }).collect(Collectors.toList());
        Long cartQuantity = cartInsertDTO.getQuantity();
        boolean check=false;
        for(int i=0;i<inventoryQuantities.size();i++){
            if(inventoryQuantities.get(i)>cartQuantity){
                cart.setUserId(cartInsertDTO.getUserId());
                cart.setProductId(cartInsertDTO.getProductId());
                cart.setQuantity(cartInsertDTO.getQuantity());
                cart.setActive(true);
                cartRepository.save(cart);
                check = true;
                break;
            }
        }
        if(check==false){
            throw new QuantityOrderException("The number of orders exceeds the quantity in stock!");
        }
    }//Tạo cart mới
    @Override
    public CartView findCartViewById(Long id){
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CartNotFoundException("Cart Id not exist!"));
        Product product = productClient.findByProductId(cart.getProductId()).getData();
        Long amount = product.getPrice() * cart.getQuantity();
        return CartView.builder()
                .id(id)
                .userId(cart.getUserId())
                .productId(cart.getProductId())
                .productName(product.getProductLine().getName())
                .price(product.getPrice())
                .quantity(cart.getQuantity())
                .amount(amount)
                .build();
    }
    @Override
    public CartOutput findAllCart(Long userId,int pageNo,int pageSize,String sortBy,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Specification<Cart> specification = (root, cq, cb) -> QueryUtils.filter(root, cb, userId, Param.USER_ID);
        Page<Cart> carts = cartRepository.findAll(specification,pageable);
        List<CartView> cartViews = carts.getContent().stream().map(cart -> findCartViewById(cart.getId())).collect(Collectors.toList());
        return CartOutput.builder()
                    .content(cartViews)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(carts.getTotalElements())
                    .build();
    }
}
