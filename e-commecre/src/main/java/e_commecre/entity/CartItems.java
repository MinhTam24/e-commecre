package e_commecre.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItems {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	long id;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonBackReference
	Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "productdetail_id")
	ProductDetail productDetail;
	
	String size;
	
	int quantity;
	
	double price;
}
