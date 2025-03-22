package e_commecre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e_commecre.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
		
	List<Product> findAll(Sort sort);
	 @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
	    Page<Product> findByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);
	 

}
