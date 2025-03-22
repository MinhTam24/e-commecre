package e_commecre.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "product_detail")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	int stockQuantity;
	
	String description;
	
	double price;
	
	int size;
	
	String color;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	Product productId;
	
	@OneToMany(mappedBy = "productDetail")
	List<OrderDetail> orderDetail;
	
}
