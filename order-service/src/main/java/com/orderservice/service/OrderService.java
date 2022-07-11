package com.orderservice.service;

import com.orderservice.domain.dto.output.*;
import com.orderservice.domain.entity.Order;
import com.orderservice.domain.model.Report.*;
import com.orderservice.domain.model.input.OrderInput;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<OrderDTO> findAllOrder(Long userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    Page<OrderDetailDTO> findAllOrderDetailDTO(Long userId,Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    void insert(OrderInput orderInput);

    OrderDetailDTO findOrderDetailDTOByOrderId(Long orderId);

    OrderDTO findById(Long id);

    void deleteById(Long id);

    List<ReportBranch> getReportByBranch();

    List<ReportSaleByDay> getReportSaleByDay();

    List<ReportSaleByMonth> getReportSaleByMonth();

    List<ReportByYear> getReportSaleByYear();

    List<SaleStaffDTO> getReportSaleStaff();

    ReportAll getReportAll();

    List<TopProduct> getTopProduct(Long top);

    void updateStatusOrder(OrderDTO order);

    void cancelOrder(OrderDTO order);

    OrderTest findAllOrderTest(Integer pageNo, Integer pageSize, String sortBy, String sortDir);
}
