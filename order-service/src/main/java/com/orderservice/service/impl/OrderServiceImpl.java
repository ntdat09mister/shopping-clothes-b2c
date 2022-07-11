package com.orderservice.service.impl;

import com.orderservice.domain.dto.input.CartUpdateDTO;
import com.orderservice.domain.dto.output.*;
import com.orderservice.domain.entity.Cart;
import com.orderservice.domain.entity.Order;
import com.orderservice.domain.entity.OrderItem;
import com.orderservice.domain.model.Report.*;
import com.orderservice.domain.model.input.OrderInput;
import com.orderservice.domain.model.output.Inventory;
import com.orderservice.domain.model.output.Product;
import com.orderservice.domain.model.output.SaleStaff;
import com.orderservice.domain.model.output.User;
import com.orderservice.exception.CartNotFoundException;
import com.orderservice.exception.OrderNotFoundException;
import com.orderservice.exception.QuantityOrderException;
import com.orderservice.repository.CartRepository;
import com.orderservice.repository.OrderItemRepository;
import com.orderservice.repository.root.Param;
import com.orderservice.service.client.ProductClient;
import com.orderservice.service.client.UserClient;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import com.orderservice.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Not found order !"));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
        List<ProductOrderDTO> productOrderDTOS = orderItems.stream().map(orderItem -> {
            Product product = productClient.findByProductId(orderItem.getProductId()).getData();
            ProductOrderDTO productOrderDTO = ProductOrderDTO.builder().quantity(orderItem.getQuantity()).name(product.getProductLine().getName()).color(product.getColor()).build();
            return productOrderDTO;
        }).collect(Collectors.toList());
        return OrderDTO.builder().orderId(order.getId()).status(order.getStatus()).productOrderDTOS(productOrderDTOS).orderTotalAmount(order.getTotalAmount()).build();
    }//Tìm kiếm Order theo orderId

    @Override
    public List<OrderDTO> findAllOrder(Long userId) {
        Specification<Order> specification = (root, cq, cb) -> QueryUtils.filter(root, cb, userId, Param.USER_ID);
        return orderRepository.findAll(specification).stream().map(order -> findById(order.getId())).collect(Collectors.toList());
    }//Tìm tất cả Order

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }//Xóa order theo OrderId

    @Override
    public List<ReportBranch> getReportByBranch() {
        return orderRepository.getReportByBranch();
    }//Report số lượng orders từng Branch

    @Override
    public List<ReportSaleByDay> getReportSaleByDay() {
        return orderRepository.getReportSaleByDay();
    }//Report báo cáo sale theo ngày

    @Override
    public List<ReportSaleByMonth> getReportSaleByMonth() {
        return orderRepository.getReportSaleByMonth();
    }//Report báo cáo sale theo tháng

    @Override
    public List<ReportByYear> getReportSaleByYear() {
        return orderRepository.getReportSaleByYear();
    }//Report báo cáo sale theo năm

    @Override
    public List<SaleStaffDTO> getReportSaleStaff() {
        List<ReportBySaleStaff> reportBySaleStaffs = orderRepository.getReportBySaleStaff();
        List<SaleStaffDTO> saleStaffDTOS = reportBySaleStaffs.stream().map(reportBySaleStaff -> {
            SaleStaff saleStaff = userClient.findByStaffId(reportBySaleStaff.getIdStaff()).getData();
            SaleStaffDTO saleStaffDTO = SaleStaffDTO.builder().reportBySaleStaff(reportBySaleStaff).name(saleStaff.getName()).build();
            return saleStaffDTO;
        }).collect(Collectors.toList());
        return saleStaffDTOS;
    }

    @Override
    public ReportAll getReportAll() {
        return orderRepository.getReportAll();
    }

    @Override
    public List<TopProduct> getTopProduct(Long top) {
        List<ReportTopProduct> reportTopProducts = orderRepository.getTopProduct(top);
        List<TopProduct> topProducts = reportTopProducts.stream().map(reportTopProduct -> { // can than cai reportTopProducts1
            Product product = productClient.findByProductId(reportTopProduct.getProductID()).getData();
            TopProduct topProduct = TopProduct.builder().reportTopProduct(reportTopProduct).name(product.getProductLine().getName()).imageUrl(product.getImages().get(0).getUrl()).price(product.getPrice()).build();
            return topProduct;
        }).collect(Collectors.toList());
        return topProducts;
    }

    @Override
    public OrderDetailDTO findOrderDetailDTOByOrderId(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Not found order!"));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
        User user = userClient.findByUserId(order.getUserId()).getData();
        UserOrderDetailDTO userOrderDetailDTO = UserOrderDetailDTO.builder().name(user.getName()).address(user.getAddress()).phone(user.getPhone()).build();
        List<ProductOrderDetailDTO> productOrderDetailDTOS = orderItems.stream().map(orderItem -> {
            Long amount = orderItem.getQuantity() * productClient.findByProductId(orderItem.getProductId()).getData().getPrice();
            Product product = productClient.findByProductId(orderItem.getProductId()).getData();
            ProductOrderDetailDTO productOrderDetailDTO = ProductOrderDetailDTO.builder().quantity(orderItem.getQuantity()).name(product.getProductLine().getName()).color(product.getColor()).size(product.getSize()).amount(amount).build();
            return productOrderDetailDTO;
        }).collect(Collectors.toList());
        return OrderDetailDTO.builder().orderId(order.getId()).status(order.getStatus()).productOrderDetailDTOS(productOrderDetailDTOS).createdAt(order.getCreatedAt()).totalAmount(order.getTotalAmount()).paymentId(order.getPaymentId()).userOrderDetailDTO(userOrderDetailDTO).build();
    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailDTO(Long userId) {
        Specification<Order> specification = (root, cq, cb) -> QueryUtils.filter(root, cb, userId, Param.USER_ID);
        return orderRepository.findAll(specification).stream().map(order -> findOrderDetailDTOByOrderId(order.getId())).collect(Collectors.toList());
    }

    @Override
    public void insert(OrderInput orderInput) {
        //Map từng phần tử cart vào List<Cart> carts
        List<Cart> carts = orderInput.getCartIds().stream().map(cartId -> cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Not found cart"))).collect(Collectors.toList());
        //Map List<Cart> carts bên trên vào List<CartDTO>
        List<CartUpdateDTO> cartUpdateDTOS = carts.stream().map(cart -> modelMapper.map(cart, CartUpdateDTO.class)).collect(Collectors.toList());
        Map<Long, Long> cartBranch = new HashedMap<>();
        for (int i = 0; i < carts.size(); i++) {
            List<Inventory> inventories = productClient.findByProductId(carts.get(i).getProductId()).getData().getInventories();
            for (int j = 0; j < inventories.size(); j++) {
                if (inventories.get(j).getQuantity() > carts.get(i).getQuantity()) {
                    cartBranch.put(carts.get(i).getId(), inventories.get(j).getBranchId());//Chọn branch cho cart item
                    break;
                }
            }
        }
        List<Cart> wrongCarts = new ArrayList<>();
        if (cartBranch.size() < carts.size()) {
            for (int i = 0; i < carts.size(); i++) {
                Long cartId = carts.get(i).getId();
                if (!cartBranch.containsKey(cartId)) {
                    wrongCarts.add(carts.get(i));
                }
            }
            List<Product> products = wrongCarts.stream().map(cart -> productClient.findByProductId(cart.getProductId()).getData()).collect(Collectors.toList());
            List<String> productNames = products.stream().map(product -> product.getProductLine().getName()).collect(Collectors.toList()); //
            String productNameStr = String.join(", ", productNames);
            throw new QuantityOrderException(String.format("Product %s of orders exceeds the quantity in stock!", productNameStr));
        }
        // Tính tổng hóa đơn = Sử dụng stream.reduce() 2 tham số là total và amount kiểu kết hợp total + amount trả về tổng tiền hóa đơn
        final Long totalAmount = cartUpdateDTOS.stream().map(cart -> productClient.findByProductId(cart.getProductId()).getData().getPrice() * cart.getQuantity()).reduce(0L, (total, amount) -> total + amount);
        Order order = Order.builder().userId(cartUpdateDTOS.get(0).getUserId()).paymentId(1l).createdAt(LocalDateTime.now()).totalAmount(totalAmount).build();
        //Map saleStaff nhập ở trên vào bảng order, set status khi tạo order thành công là đang giao hàng!
        order.setIdSaleStaff(orderInput.getIdSaleStaff());
        order.setStatus(1L);
        //Save in order
        order = orderRepository.save(order);
        final Long orderId = order.getId();
        // Map List<Cart> to List<OrderItem>
        List<OrderItem> orderItems = cartUpdateDTOS.stream().map(cart -> OrderItem.builder().orderId(orderId).productId(cart.getProductId()).branchId(1L).quantity(cart.getQuantity()).build()).collect(Collectors.toList());
        // Save all to List<OrderItem> orderItems
        orderItemRepository.saveAll(orderItems);
        // Cart nào đc map vào orderItem thì setActive trong Cart=false ( đã đc thêm vào order, trc đó thì lúc tạo cart set=true )
        carts.stream().forEach(cart -> {
            cart.setActive(false);
        });
        //Save to List<Cart>
        cartRepository.saveAll(carts);
        // Trừ quantity product đã đc order trong bảng Inventory của productClient
        List<InventoryDTO> inventoryDTOs = orderItems.stream().map(orderItem -> modelMapper.map(orderItem, InventoryDTO.class)).collect(Collectors.toList());
        productClient.updateQuantities(inventoryDTOs);
    }

    @Override
    public void updateStatusOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Not found order!"));
        order.setStatus(orderDTO.getStatus());
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Not found order!"));
        Long status = order.getStatus();
        if (status == 3 || status == 4) {
            throw new CartNotFoundException("Orders are being delivered, cannot be canceled!");
        } else if (status == 5) {
            {
                throw new CartNotFoundException("This order has been canceled!");
            }
        } else {
            order.setStatus(5L);
            orderRepository.save(order);
            //Nếu save được trạng thái bằng 5 ( hủy hàng ) thì update trả lại quantity cho inventory (Cộng với số âm của quantity)
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
            List<InventoryDTO> inventoryDTOs = orderItems.stream().map(orderItem -> modelMapper.map(orderItem, InventoryDTO.class)).collect(Collectors.toList());
            inventoryDTOs.stream().forEach(inventoryDTO -> inventoryDTO.setQuantity(-inventoryDTO.getQuantity()));
            productClient.updateQuantities(inventoryDTOs);
        }
    }
}