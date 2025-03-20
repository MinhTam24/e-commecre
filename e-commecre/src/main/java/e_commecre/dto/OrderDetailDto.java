package e_commecre.dto;

import java.util.List;

import e_commecre.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	
	long id;
	
	int quantity;
	
	double unitPrice;
	
	double totalPrice;
	
	ProductDto productDtos;
	
	public static OrderDetailDto convertToOrderDetailDto(OrderDetail orderDetail) {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setId(orderDetail.getId());
		orderDetailDto.setQuantity(orderDetail.getQuantity());
		orderDetailDto.setUnitPrice(orderDetail.getUnitPrice());
		orderDetailDto.setProductDtos(ProductDto.ConvertToProductDto(orderDetail.getProduct()));
		return orderDetailDto;
	}
	
}
