package com.orderservice.domain.model.Report;

public interface ReportSaleByMonth {
    Long getMonth();
    Long getTotalOrder();
    Long getTotalAmount();
}
