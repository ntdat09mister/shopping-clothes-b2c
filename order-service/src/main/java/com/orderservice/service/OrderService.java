package com.orderservice.service;

import com.orderservice.domain.dto.output.OrderDTO;
import com.orderservice.domain.dto.output.OrderDetailDTO;
import com.orderservice.domain.dto.output.SaleStaffDTO;
import com.orderservice.domain.dto.output.TopProduct;
import com.orderservice.domain.model.Report.*;
import com.orderservice.domain.model.input.OrderInput;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAllOrder(Long userId);

    List<OrderDetailDTO> findAllOrderDetailDTO(Long userId);

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

}
