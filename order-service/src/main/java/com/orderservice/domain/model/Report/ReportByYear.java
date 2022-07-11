package com.orderservice.domain.model.Report;

public interface ReportByYear {
    Long getYear();
    Long getTotalOrders();
    Long getTotalAmount();
}
