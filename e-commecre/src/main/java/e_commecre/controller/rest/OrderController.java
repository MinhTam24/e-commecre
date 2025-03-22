package e_commecre.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.OrderDto;
import e_commecre.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/api/order/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id) {
            return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/orders")
    public Page<OrderDto> getAllOrders(
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        
        return orderService.getAllOrder(pageRequest);
    }
    
    @GetMapping("/orders/status/{status}")
    public Page<OrderDto> getAllOrdersByStatus(
    		@PathVariable("status") String status ,
    		@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, 
    		@RequestParam(name = "pageSize", defaultValue = "10")int pageSize){
    	PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
    	return orderService.getAllOrderByStatus(status, pageRequest);    	
	}

    @GetMapping("/orders/customer/{customerId}")
    public Page<OrderDto> getOrdersByCustomerId(
        @PathVariable("customerId") long customerId, 
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        
        // Tạo PageRequest từ pageNumber và pageSize
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        
        return orderService.getOrderByCustomerId(customerId, pageRequest);
    }
    
    

    @GetMapping("/orders/product/{productId}")
    public Page<OrderDto> getOrderByProductId(
        @PathVariable("productId") long productId, 
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        
        // Tạo PageRequest từ pageNumber và pageSize
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        
        return orderService.getOrderByProductId(productId, pageRequest);
    }
    
    
    @GetMapping("/orders/price")
    public Page<OrderDto> getOrderByTotalPrice(
            @RequestParam(name = "minPrice") double minPrice,
            @RequestParam(name = "maxPrice") double maxPrice,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
		
    	   PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
       
           return orderService.getOrderByTotalPrice(minPrice, maxPrice, pageRequest);      
    }

    
    
}
