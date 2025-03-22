package e_commecre.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import e_commecre.dto.OrderDetailDto;
import e_commecre.dto.OrderDto;
import e_commecre.entity.Account;
import e_commecre.entity.Order;
import e_commecre.entity.OrderDetail;
import e_commecre.entity.Product;
import e_commecre.entity.ProductDetail;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.AccountRepository;
import e_commecre.repository.OrderDetailRepository;
import e_commecre.repository.OrderRepository;
import e_commecre.repository.ProductDetailRepository;
import e_commecre.repository.ProductRepository;
import e_commecre.service.AccountService;
import e_commecre.service.OrderService;
import e_commecre.ultil.ConstUltil;
import e_commecre.ultil.OrderStatus;
import e_commecre.ultil.PaymentStatus;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    ProductDetailRepository productRepository;
    
    @Autowired
    OrderDetailRepository orderDetailRepository;
    
    

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
        Page<Order> pageOrder = orderRepository.findByStatus(status, pageRequest);
        if (pageOrder.hasContent()) {
            return pageOrder.map(OrderDto::convertToOrderDto);
        }
        throw new ResouceNotFoundException("Not Found any order");    	
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
    	
    	AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);
    	
    	Account customer = accountRepository.findById(orderDto.getCustomerId())
    			.orElseThrow(() -> new ResouceNotFoundException("Not Found customer with ID: " + orderDto.getCustomerId()));
    	
    	Order order = new Order();
    	order.setCustomerId(customer);
    	order.setOrderNote(orderDto.getOrderNote());
    	order.setShippingAddress(orderDto.getShippingAddress());
    	order.setShippingPhonenumber(orderDto.getShippingPhonenumber());
    	order.setPaymentStatus(PaymentStatus.fromString(orderDto.getPaymentStatus()));
    	order.setStatus(OrderStatus.fromString(orderDto.getStatus()));
    	
    	Order orderSaved = orderRepository.save(order);
    	
    	 List<OrderDetail> listOrderDetail = orderDto.getOrderDetailDtos().stream()
    	            .map(detailDto -> {
    	                ProductDetail productDetail = productRepository.findById(detailDto.getProductDetailDto().getId())
    	                		.orElseThrow(() -> new ResouceNotFoundException("Not Found ProductDetail With Id " + detailDto.getProductDetailDto().getId() ));

    	                OrderDetail orderDetail = new OrderDetail();
    	                orderDetail.setOrderId(orderSaved);
    	                orderDetail.setProductDetail(productDetail);
    	                orderDetail.setQuantity(detailDto.getQuantity());
    	                orderDetail.setTotalPrice(productDetail.getPrice() * detailDto.getQuantity());
    	                orderDetail.setUnitPrice(productDetail.getPrice());
    	                
    	                Double unitPrice = productDetail.getPrice();
    	                orderDetail.setUnitPrice(unitPrice);
    	                
    	                Double totalPrice = unitPrice * detailDto.getQuantity();
    	                orderDetail.setTotalPrice(totalPrice);

    	                
    	                totalAmount.updateAndGet(v -> v += totalPrice);
    	                return orderDetail; 
    	                
    	        }).toList();
    	 
     	order.setTotalAmount(totalAmount.get());    	

     	orderDetailRepository.saveAll(listOrderDetail);

    	orderRepository.save(orderSaved);
    	
    	return orderDto;
    }

    @Override
    public OrderDto updateOrder(long id, OrderDto orderDto) {
    	Order order = orderRepository.findById(orderDto.getId())
    			.orElseThrow(() -> new ResouceNotFoundException("Not Found Order With ID: " + orderDto.getId()));
    	order.setOrderNote(orderDto.getOrderNote());
    	order.setShippingAddress(orderDto.getShippingAddress());
    	order.setShippingPhonenumber(orderDto.getShippingPhonenumber());
    	orderRepository.save(order);
        return orderDto;
    }

    @Override
    public void deleteOrder(long id) {
        // Logic for deleting an order
    }

    @Override
    public OrderDto updateOrderStatus(long id, String status) {
    	Order order = orderRepository.findById(id)
    			.orElseThrow(() -> new ResouceNotFoundException("Not Found Order With ID: " + id));
    	order.setStatus(OrderStatus.fromString(status));
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
    	Page<Order> pageOrder = orderRepository.findByProductId(productId, pageRequest);
    	if(pageOrder.hasContent()) {
    		return pageOrder.map(OrderDto::convertToOrderDto);
    	}
    	throw new ResouceNotFoundException("Not Found any order");
    }

}
