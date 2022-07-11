package com.orderservice.controller;

import com.orderservice.common.DefaultPagination;
import com.orderservice.common.HttpStatusCode;
import com.orderservice.domain.dto.output.OrderDTO;
import com.orderservice.domain.message.BaseMessage;
import com.orderservice.domain.model.input.OrderInput;
import com.orderservice.domain.model.output.Product;
import com.orderservice.domain.model.output.User;
import com.orderservice.resolver.UserInfo;
import com.orderservice.service.OrderService;
import com.orderservice.service.client.ProductClient;
import com.orderservice.service.report.ReportSaleByBranchExcel;
import com.orderservice.service.report.ReportSaleByDayExcel;
import com.orderservice.service.report.ReportSaleStaffExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController extends BaseController {
    private final OrderService orderService;

    private final ReportSaleStaffExcel reportSaleStaffExcel;

    private final ReportSaleByBranchExcel reportSaleByBranchExcel;

    private final ReportSaleByDayExcel reportSaleByDayExcel;

    /**
     * Find order by id
     *
     * @param id
     * @return successResponse + Order
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Find order by order id")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Find successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return successResponse(orderService.findById(id));
    }

    /**
     * Find all order
     *
     * @return successResponse + List<OrderDTO>
     */
    @GetMapping("")
    @Operation(summary = "Get all orders of the logged in user")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Find successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> findAllOrder(@UserInfo User user,
                                          @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_NUMBER) Integer pageNo,
                                          @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_SIZE) Integer pageSize,
                                          @RequestParam(required = false, defaultValue = DefaultPagination.SORT_BY) String sortBy,
                                          @RequestParam(required = false, defaultValue = DefaultPagination.SORT_DIRECTION) String sortDir
                                          ) {
        return successResponse(orderService.findAllOrder(user.getId(),pageNo,pageSize,sortBy,sortDir));
    }

    /**
     * Find order detail by order id
     *
     * @param orderId
     * @return successResponse + OrderDetailDTO
     */
    @GetMapping("detail/{orderId}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Find order detail by order id")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Find successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> findOrderDTObyIdOrder(@PathVariable Long orderId) {
        return successResponse(orderService.findOrderDetailDTOByOrderId(orderId));
    }

    /**
     * Find all order detail
     *
     * @return successResponse + List<OrderDetailDTO>
     */
    @GetMapping("detail")
    @Operation(summary = "Get all orders detail of the logged in user")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Find successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> findAllOrderDTO(@UserInfo User user,
                                             @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_NUMBER) Integer pageNo,
                                             @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_SIZE) Integer pageSize,
                                             @RequestParam(required = false, defaultValue = DefaultPagination.SORT_BY) String sortBy,
                                             @RequestParam(required = false, defaultValue = DefaultPagination.SORT_DIRECTION) String sortDir
                                             ) {
        return successResponse(orderService.findAllOrderDetailDTO(user.getId(),pageNo,pageSize,sortBy,sortDir));
    }

    /**
     * Insert order
     *
     * @param orderInput
     * @return successResponse
     */
    @PostMapping
    @Operation(summary = "Insert order")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Insert successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> createNewOrder(@RequestBody OrderInput orderInput) {
        orderService.insert(orderInput);
        return successResponse("Created order successfully!");
    }

    /**
     * Delete order by id
     *
     * @param id
     * @return successResponse
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Delete order by order id")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Delete successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
        return successResponse("Deleted order successfully");
    }

    /**
     * Report Branch
     *
     * @return successResponse
     */

    @GetMapping("report/branch")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Get report by branch")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportByBranch() {
        return successResponse(orderService.getReportByBranch());
    }

    /**
     * Report Day
     *
     * @return successResponse
     */
    @GetMapping("report/sale/day")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Get report sale by day")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportSaleByDay() {
        return successResponse(orderService.getReportSaleByDay());
    }

    /**
     * Report Month
     *
     * @return successResponse
     */
    @GetMapping("report/sale/month")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Get report sale by month")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportSaleByMonth() {
        return successResponse(orderService.getReportSaleByMonth());
    }

    /**
     * Report year
     *
     * @return successResponse
     */
    @GetMapping("report/sale/year")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Get report by year")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportSaleByYear() {
        return successResponse(orderService.getReportSaleByYear());
    }

    /**
     * Report Sale Staff
     *
     * @return successResponse
     */
    @GetMapping("report/salestaff")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Get report by sale staff")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportBySaleStaff() {
        return successResponse(orderService.getReportSaleStaff());
    }


    /**
     * Report All
     *
     * @return
     */

    @GetMapping("report/all")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Report all")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getReportAll() {
        return successResponse(orderService.getReportAll());
    }

    @GetMapping("report/top/products")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Report top sale products")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> getTopProducts(@Param("top") Long top) {
        return successResponse(orderService.getTopProduct(top));
    }

    /**
     * Update status order
     *
     * @param order
     * @return
     */
    @PutMapping("update/status")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Update status order")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> updateStatusOrder(@RequestBody @Valid OrderDTO order) {
        orderService.updateStatusOrder(order);
        return successResponse("Update status order successfully!");
    }

    /**
     * Cancel order
     *
     * @param order
     * @return
     */
    @PutMapping("cancel")
    @Operation(summary = "Report cancel order")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> cancelOrder(@RequestBody @Valid OrderDTO order) {
        orderService.cancelOrder(order);
        return successResponse("Cancel order successfully!");
    }

    /**
     * Export excel sale staff
     *
     * @throws IOException
     */
    @GetMapping("export/salestaff")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Export file excel report Sale Staff")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public void exportSaleStaff() throws IOException {
        reportSaleStaffExcel.export();
    }

    /**
     * Export excel report sale by branch
     *
     * @throws IOException
     */
    @GetMapping("export/branch")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Export file excel report sale by branch")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public void exportSaleBranch() throws IOException {
        reportSaleByBranchExcel.export();
    }

    /**
     * Export excel report by day
     *
     * @throws IOException
     */
    @GetMapping("export/day")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Export file excel report sale by day")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public void exportSaleByDay() throws IOException {
        reportSaleByDayExcel.export();
    }
    @GetMapping("test")
    public ResponseEntity<?> findAllOrderTest(@RequestParam(required = false, defaultValue = DefaultPagination.PAGE_NUMBER) Integer pageNo,
                                              @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_SIZE) Integer pageSize,
                                              @RequestParam(required = false, defaultValue = DefaultPagination.SORT_BY) String sortBy,
                                              @RequestParam(required = false, defaultValue = DefaultPagination.SORT_DIRECTION) String sortDir
                                              ){
        return successResponse(orderService.findAllOrderTest(pageNo, pageSize, sortBy, sortDir));
    }
}
