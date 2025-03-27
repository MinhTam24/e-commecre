package e_commecre.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_detail_size")
public class ProductDetailSize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	int quantity;

	@ManyToOne
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;

	@ManyToOne
	@JoinColumn(name = "size_id")
	private Size size;

}
