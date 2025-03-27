package e_commecre.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "size")
@Data
public class Size {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String name;
	
	@OneToMany(mappedBy = "size")
	List<ProductDetailSize> productDetailSize;

}
