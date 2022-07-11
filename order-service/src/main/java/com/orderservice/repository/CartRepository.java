package com.orderservice.repository;

import com.orderservice.domain.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    @Modifying// Phải sử dụng cái này vì Spring Data JPA ko hỗ trợ chức năng insert delete bằng native query
    @Transactional//Khai báo cái delete bên dưới sẽ đc thực thi trong Transactional
    @Query(value = "delete from cart c where c.user_id = (:userId)", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
    Page<Cart> findByUserIdAndActive(Long userId, Boolean active, Pageable pageable);
}
