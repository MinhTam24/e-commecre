package e_commecre.dto;

import java.time.LocalDateTime;
import java.util.List;

import e_commecre.entity.Account;
import e_commecre.entity.Order;
import e_commecre.entity.OrderDetail;
import e_commecre.ultil.ConstUltil;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	long id;

	long customerId;

	String customerEmail;

	String shippingAddress;

	String shippingPhonenumber;

	String customerPhone;

	List<OrderDetailDto> orderDetailDtos;

	String orderDate;

	double totalAmount;

	String status;
	
	String orderNote;

	String paymentStatus;

	public static OrderDto convertToOrDerDtoWithOrderDetail(Order order) {

		List<OrderDetailDto> listOrderDetailDto = order.getOrderDetails().stream()
				.map(OrderDetailDto::convertToOrderDetailDto).toList();

		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setShippingAddress(order.getShippingAddress());
		orderDto.setShippingPhonenumber(order.getShippingPhonenumber());
		orderDto.setOrderNote(order.getOrderNote());
		orderDto.setCustomerEmail(order.getCustomerId().getEmail());
		orderDto.setCustomerPhone(order.getCustomerId().getPhoneNumber());
		orderDto.setCustomerId(order.getCustomerId().getId());
		orderDto.setOrderDate(order.getOrderDate().format(ConstUltil.DATE_TIME_FORMATTER));
		orderDto.setStatus(order.getStatus().toString());
		orderDto.setTotalAmount(order.getTotalAmount());
		orderDto.setOrderDetailDtos(listOrderDetailDto);
		orderDto.setPaymentStatus(order.getPaymentStatus().toString());

		return orderDto;
	}
	
	
	public static OrderDto convertToOrderDto(Order order) {

		List<OrderDetailDto> listOrderDetailDto = order.getOrderDetails().stream()
				.map(OrderDetailDto::convertToOrderDetailDto).toList();

		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setShippingAddress(order.getShippingAddress());
		orderDto.setShippingPhonenumber(order.getShippingPhonenumber());
		orderDto.setOrderNote(order.getOrderNote());
		orderDto.setCustomerEmail(order.getCustomerId().getEmail());
		orderDto.setCustomerPhone(order.getCustomerId().getPhoneNumber());
		orderDto.setCustomerId(order.getCustomerId().getId());
		orderDto.setOrderDate(order.getOrderDate().format(ConstUltil.DATE_TIME_FORMATTER));
		orderDto.setStatus(order.getStatus().toString());
		orderDto.setTotalAmount(order.getTotalAmount());
		orderDto.setPaymentStatus(order.getPaymentStatus().toString());

		return orderDto;
	}


}
