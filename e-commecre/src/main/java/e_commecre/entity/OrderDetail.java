package e_commecre.entity;

import java.util.List;

import e_commecre.dto.OrderDetailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	int quantity;
	
	double unitPrice;
	
	double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	Order orderId;
	
	@ManyToOne
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
	
	
//	public static OrderDetail ConvertOrderDetailDtoToOrderDtail(OrderDetailDto orderDetailDto) {
//		OrderDetailDto.builder()
//			.productDtos(null)
//		
//		
//		
//		return null;
//	}
	
}
