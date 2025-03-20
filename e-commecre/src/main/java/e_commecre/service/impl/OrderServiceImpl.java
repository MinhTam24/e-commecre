package e_commecre.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import e_commecre.dto.OrderDto;
import e_commecre.entity.Order;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.OrderRepository;
import e_commecre.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto getOrderById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundException("Not found order with Id: " + id));
        return OrderDto.convertToOrDerDtoWithOrderDetail(order);
    }

    @Override
    public Page<OrderDto> getAllOrder(PageRequest pageRequest) {
        Page<Order> pageOrder = orderRepository.findAll(pageRequest);
        if (pageOrder.hasContent()) {
            return pageOrder.map(OrderDto::convertToOrderDto);
        }
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public Page<OrderDto> getOrderByCustomerId(long id, PageRequest pageRequest) {
        Page<Order> pageOrder = orderRepository.findOrderByCustomerIdId(id, pageRequest);
        if (pageOrder.hasContent()) {
            return pageOrder.map(OrderDto::convertToOrderDto);
        }
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public Page<OrderDto> getAllOrderByStatus(String status, PageRequest pageRequest) {
        // Implement the logic to filter orders by status
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // Logic for creating a new order
        return null;
    }

    @Override
    public OrderDto updateOrder(long id, OrderDto orderDto) {
        // Logic for updating an order
        return null;
    }

    @Override
    public void deleteOrder(long id) {
        // Logic for deleting an order
    }

    @Override
    public OrderDto updateOrderStatus(long id, boolean status) {
        // Logic to update order status
        return null;
    }

    @Override
    public Page<OrderDto> getOrderByDateRange(LocalDate startDate, LocalDate endDate, PageRequest pageRequest) {
        Page<Order> pageOrder = orderRepository.findByOrderDateBetween(startDate, endDate, pageRequest);
        if (pageOrder.hasContent()) {
            return pageOrder.map(OrderDto::convertToOrderDto);
        }
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public Page<OrderDto> getOrderByTotalPrice(double minPrice, double maxPrice, PageRequest pageRequest) {
        Page<Order> pageOrder = orderRepository.findByTotalAmountBetween(minPrice, maxPrice, pageRequest);
        if (pageOrder.hasContent()) {
            return pageOrder.map(OrderDto::convertToOrderDto);
        }
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public Page<OrderDto> getOrderByPaymentMethod(String paymentMethod, PageRequest pageRequest) {
        // Implement the logic to filter orders by payment method
        throw new ResouceNotFoundException("Not Found any order");
    }

    @Override
    public boolean checkOrderExists(long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Page<OrderDto> getOrderByProductId(long productId, PageRequest pageRequest) {
        // Implement the logic to filter orders by product
        return null;
    }
}
