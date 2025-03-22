package e_commecre.entity;

import java.time.LocalDateTime;
import java.util.List;

import e_commecre.ultil.OrderStatus;
import e_commecre.ultil.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "customer_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String shippingAddress;
	
	String shippingPhonenumber;
	
	String orderNote;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Account customerId;
	
	@OneToMany(mappedBy = "orderId")
	List<OrderDetail> orderDetails;
	
	@Column(nullable = false, updatable = false, insertable = false)
	LocalDateTime orderDate;
	
	double totalAmount;
	
	@Enumerated(EnumType.STRING)  
	OrderStatus status;
	

	@Enumerated(EnumType.STRING)  
	PaymentStatus paymentStatus;
	
}
