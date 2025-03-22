package e_commecre.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDto;

@Service
public interface ProductService {
	Page<ProductDto> getListProductByCategoryId(long id, Pageable pageable);

	ProductDto getProductById(long id);

	long createProduct(ProductDto productDto);

	Page<ProductDto> filterProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice,
			LocalDate startDate, LocalDate endDate, String sortBy, String sortDirection, int page, int size);

}
