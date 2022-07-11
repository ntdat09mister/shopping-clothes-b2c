package com.orderservice.repository;

import com.orderservice.domain.entity.Order;
import com.orderservice.domain.model.Report.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query(value = "select oi.branch_id Branch,count(o.id) as TotalOrder ,sum(o.total_amount) as TotalAmount  from order_item oi join `order` o on oi.order_id = o.id group by oi.branch_id", nativeQuery = true)
    List<ReportBranch> getReportByBranch();

    @Query(value = "select table2.Date as Date, table2.TotalOrders as TotalOrders, table2.TotalAmount from (select date(table1.date) as Date,count(table1.orders) as TotalOrders, sum(table1.amount) as TotalAmount" +
            " from (select date(o.created_at) as date,o.id as orders,o.total_amount as amount from `order` o where o.status not in (\"5\") order by o.created_at desc) as table1" +
            " group by table1.date order by date(table1.date) desc limit 7) as table2 order by table2.Date asc", nativeQuery = true)
    List<ReportSaleByDay> getReportSaleByDay();

    @Query(value = "select table1.month as Month,count(table1.orders) as TotalOrder, sum(table1.amount) as TotalAmount" +
            " from (select month(o.created_at) as month,o.id as orders,o.total_amount as amount from `order` o where o.status not in (\"5\")) as table1" +
            " group by table1.month", nativeQuery = true)
    List<ReportSaleByMonth> getReportSaleByMonth();

    @Query(value = "select table1.year as Year,count(table1.orders) as TotalOrders, sum(table1.amount) as TotalAmount" +
            " from(select year(o.created_at) as year,o.id as orders,o.total_amount as amount from `order` o where o.status not in (\"5\")) as table1" +
            " group by table1.year", nativeQuery = true)
    List<ReportByYear> getReportSaleByYear();

    @Query(value = "select o.id_sale_staff as IdStaff, sum(o.total_amount) as TotalAmount from `order` o group by o.id_sale_staff", nativeQuery = true)
    List<ReportBySaleStaff> getReportBySaleStaff();

    @Query(value = "select table3.userIds as TotalUsers, table3.orderIds TotalOrders, table4.productIds as TotalProducts ,table3.amount as TotalAmount" +
            " from (select count(table1.userid) as userIds,sum(table1.ids) as orderIds,sum(table1.amount) as amount" +
            " from (select o.user_id as userid, count(o.id) as ids, sum(o.total_amount) as amount from `order` o" +
            " group by o.user_id) as table1) as table3 join" +
            " (select count(table2.productIds) as productIds" +
            " from (select distinct oi.product_id as productIds from order_item oi) as table2) as table4", nativeQuery = true)
    ReportAll getReportAll();
    @Query(value = "select table1.ids as ProductID, table1.total as TotalOrders" +
            " from (select oi.product_id as ids, count(oi.product_id) as total from order_item oi" +
            " group by oi.product_id) as table1 order by table1.total desc limit :top",nativeQuery = true)
    List<ReportTopProduct> getTopProduct(@Param("top") Long top);
}
