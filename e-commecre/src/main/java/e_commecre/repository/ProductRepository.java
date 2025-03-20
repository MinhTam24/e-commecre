package e_commecre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
		
	List<Product> findAll(Sort sort);
	
}
