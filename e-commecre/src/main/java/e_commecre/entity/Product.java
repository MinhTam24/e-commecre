package e_commecre.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String name;
	
	@OneToMany(mappedBy = "productId")
	List<ProductDetail> productDetail;
	
	@ManyToMany(mappedBy = "product")
	private List<Categories> categories;
	
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;
	
	LocalDateTime createAt;
	
}
