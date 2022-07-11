package com.orderservice.domain.model.Report;

import java.util.Date;

public interface ReportSaleByDay {
    Date getDate();
    Long getTotalOrders();
    Long getTotalAmount();
}
