package e_commecre.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e_commecre.entity.Product;
import e_commecre.entity.ProductDetail;

public interface ProductDetailRepository
		extends JpaRepository<ProductDetail, Long>, JpaSpecificationExecutor<ProductDetail> {
	Optional<ProductDetail> findByColorAndProductIdId(String color, long id);

	@Query("SELECT s.name FROM ProductDetailSize pds JOIN pds.size s WHERE pds.productDetail.id = :productDetailId")
	List<String> findSizeNamesByProductDetailId(@Param("productDetailId") Long productDetailId);

	@Query("SELECT pds.quantity FROM ProductDetailSize pds JOIN pds.size s WHERE pds.productDetail.id = :productDetailId And s.name = :sizeName")
	Optional<Integer> findSizeQuantitysByProductDetailId(@Param("productDetailId") Long productDetailId,
			@Param("sizeName") String sizeName);

}
